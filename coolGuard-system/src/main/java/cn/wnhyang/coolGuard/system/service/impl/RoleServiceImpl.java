package cn.wnhyang.coolGuard.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.wnhyang.coolGuard.common.constant.UserConstants;
import cn.wnhyang.coolGuard.common.entity.LabelValue;
import cn.wnhyang.coolGuard.common.pojo.PageResult;
import cn.wnhyang.coolGuard.common.util.CollectionUtils;
import cn.wnhyang.coolGuard.redis.constant.RedisKey;
import cn.wnhyang.coolGuard.system.convert.RoleConvert;
import cn.wnhyang.coolGuard.system.entity.RoleDO;
import cn.wnhyang.coolGuard.system.entity.RoleMenuDO;
import cn.wnhyang.coolGuard.system.mapper.RoleMapper;
import cn.wnhyang.coolGuard.system.mapper.RoleMenuMapper;
import cn.wnhyang.coolGuard.system.mapper.UserRoleMapper;
import cn.wnhyang.coolGuard.system.service.RoleService;
import cn.wnhyang.coolGuard.system.vo.role.RoleCreateVO;
import cn.wnhyang.coolGuard.system.vo.role.RolePageVO;
import cn.wnhyang.coolGuard.system.vo.role.RoleUpdateVO;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static cn.wnhyang.coolGuard.common.exception.util.ServiceExceptionUtil.exception;
import static cn.wnhyang.coolGuard.system.error.SystemErrorCode.*;


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
    public RoleDO getRole(Long id) {
        return roleMapper.selectById(id);
    }

    @Override
    public PageResult<RoleDO> getRolePage(RolePageVO reqVO) {
        return roleMapper.selectPage(reqVO);
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
