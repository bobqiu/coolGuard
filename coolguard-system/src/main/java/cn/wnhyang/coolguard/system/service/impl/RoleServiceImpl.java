package cn.wnhyang.coolguard.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.wnhyang.coolguard.common.constant.UserConstants;
import cn.wnhyang.coolguard.common.entity.LabelValue;
import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.common.util.CollectionUtils;
import cn.wnhyang.coolguard.redis.constant.RedisKey;
import cn.wnhyang.coolguard.system.convert.RoleConvert;
import cn.wnhyang.coolguard.system.entity.RoleDO;
import cn.wnhyang.coolguard.system.entity.RoleMenuDO;
import cn.wnhyang.coolguard.system.mapper.RoleMapper;
import cn.wnhyang.coolguard.system.mapper.RoleMenuMapper;
import cn.wnhyang.coolguard.system.mapper.UserRoleMapper;
import cn.wnhyang.coolguard.system.service.PermissionService;
import cn.wnhyang.coolguard.system.service.RoleService;
import cn.wnhyang.coolguard.system.vo.role.RoleCreateVO;
import cn.wnhyang.coolguard.system.vo.role.RolePageVO;
import cn.wnhyang.coolguard.system.vo.role.RoleRespVO;
import cn.wnhyang.coolguard.system.vo.role.RoleUpdateVO;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static cn.wnhyang.coolguard.common.exception.util.ServiceExceptionUtil.exception;
import static cn.wnhyang.coolguard.system.error.SystemErrorCode.*;


/**
 * 角色
 *
 * @author wnhyang
 * @since 2023/05/14
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleMapper roleMapper;

    private final RoleMenuMapper roleMenuMapper;

    private final UserRoleMapper userRoleMapper;

    private final PermissionService permissionService;

    @Override
    public List<RoleDO> getRoleList(Collection<Long> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            return Collections.emptyList();
        }
        return roleMapper.selectByIds(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createRole(RoleCreateVO reqVO) {
        validateRoleForCreateOrUpdate(null, reqVO.getName(), reqVO.getCode());
        RoleDO roleDO = RoleConvert.INSTANCE.convert(reqVO);
        roleMapper.insert(roleDO);
        if (CollectionUtil.isNotEmpty(reqVO.getMenuIds())) {
            roleMenuMapper.insertBatch(CollectionUtils.convertList(reqVO.getMenuIds(),
                    menuId -> new RoleMenuDO().setRoleId(roleDO.getId()).setMenuId(menuId)));
        }
        return roleDO.getId();
    }

    @Override
    @CacheEvict(value = RedisKey.ROLE, key = "#reqVO.id")
    @Transactional(rollbackFor = Exception.class)
    public void updateRole(RoleUpdateVO reqVO) {
        // 校验是否可以更新
        validateRoleForUpdate(reqVO.getId());
        // 校验角色的唯一字段是否重复
        validateRoleForCreateOrUpdate(reqVO.getId(), reqVO.getName(), reqVO.getCode());

        // 更新到数据库
        RoleDO roleDO = RoleConvert.INSTANCE.convert(reqVO);
        roleMenuMapper.deleteByRoleId(roleDO.getId());
        if (CollectionUtil.isNotEmpty(reqVO.getMenuIds())) {
            roleMenuMapper.insertBatch(CollectionUtils.convertList(reqVO.getMenuIds(),
                    menuId -> new RoleMenuDO().setRoleId(roleDO.getId()).setMenuId(menuId)));
        }
        roleMapper.updateById(roleDO);
    }

    @Override
    @CacheEvict(value = RedisKey.ROLE, key = "#id")
    @Transactional(rollbackFor = Exception.class)
    public void deleteRole(Long id) {
        // 校验是否可以删除
        // 1、存在角色
        // 2、没有用户关联角色
        validateRoleForDelete(id);
        // 标记删除
        roleMapper.deleteById(id);
        // 删除相关数据
        roleMenuMapper.deleteByRoleId(id);
    }

    @Override
    public RoleRespVO getRole(Long id) {
        RoleDO roleDO = roleMapper.selectById(id);
        Set<Long> menuIds = permissionService.getMenuIdListByRoleId(roleDO.getId());
        RoleRespVO respVO = RoleConvert.INSTANCE.convert(roleDO);
        respVO.setMenuIds(menuIds);
        return respVO;
    }

    @Override
    public PageResult<RoleRespVO> getRolePage(RolePageVO reqVO) {
        PageResult<RoleDO> pageResult = roleMapper.selectPage(reqVO);
        List<RoleRespVO> roleRespVOList = pageResult.getList().stream().map(role -> {
            Set<Long> menuIds = permissionService.getMenuIdListByRoleId(role.getId());
            RoleRespVO respVO = RoleConvert.INSTANCE.convert(role);
            respVO.setMenuIds(menuIds);
            return respVO;
        }).collect(Collectors.toList());

        return new PageResult<>(roleRespVOList, pageResult.getTotal());
    }

    @Override
    public List<RoleDO> getRoleList(Boolean status) {
        return roleMapper.selectList();
    }

    @Override
    public List<LabelValue> getLabelValueList() {
        return CollectionUtils.convertList(roleMapper.selectList(), RoleDO::getLabelValue);
    }

    private void validateRoleForDelete(Long id) {
        validateRoleForUpdate(id);
        Long count = userRoleMapper.selectCountByRoleId(id);
        if (count > 0) {
            throw exception(ROLE_HAS_USER);
        }
    }

    private void validateRoleForUpdate(Long id) {
        RoleDO roleDO = roleMapper.selectById(id);
        if (roleDO == null) {
            throw exception(ROLE_NOT_EXISTS);
        }
        // 内置角色，不允许删除
        if (UserConstants.ADMINISTRATOR_VALUE.equals(roleDO.getCode())) {
            throw exception(ROLE_CAN_NOT_UPDATE_SYSTEM_TYPE_ROLE);
        }
    }

    private void validateRoleForCreateOrUpdate(Long id, String name, String code) {
        // 0. 超级管理员，不允许创建
        if (UserConstants.ADMINISTRATOR_VALUE.equals(code)) {
            throw exception(ROLE_ADMIN_CODE_ERROR, code);
        }
        // 1. 该 name 名字被其它角色所使用
        RoleDO roleDO = roleMapper.selectByName(name);
        if (roleDO != null && !roleDO.getId().equals(id)) {
            throw exception(ROLE_NAME_DUPLICATE, name);
        }
        // 2. 是否存在相同编码的角色
        if (!StringUtils.hasText(code)) {
            return;
        }
        // 该 value 编码被其它角色所使用
        roleDO = roleMapper.selectByCode(code);
        if (roleDO != null && !roleDO.getId().equals(id)) {
            throw exception(ROLE_CODE_DUPLICATE, code);
        }
    }
}
