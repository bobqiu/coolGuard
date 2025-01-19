package cn.wnhyang.coolGuard.system.service;


import cn.wnhyang.coolGuard.entity.LabelValue;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.system.entity.RoleDO;
import cn.wnhyang.coolGuard.system.vo.role.RoleCreateVO;
import cn.wnhyang.coolGuard.system.vo.role.RolePageVO;
import cn.wnhyang.coolGuard.system.vo.role.RoleUpdateVO;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import static cn.wnhyang.coolGuard.util.CollectionUtils.convertSet;


/**
 * 角色信息表
 *
 * @author wnhyang
 * @since 2023/05/14
 */
public interface RoleService {

    /**
     * 获得角色列表
     *
     * @param ids 角色编号数组
     * @return 角色列表
     */
    List<RoleDO> getRoleList(Collection<Long> ids);

    /**
     * 获取角色编码
     *
     * @param ids 角色编号数组
     * @return 角色编码
     */
    default Set<String> getRoleValueList(Set<Long> ids) {
        return convertSet(getRoleList(ids), RoleDO::getCode);
    }

    /**
     * 创建角色
     *
     * @param reqVO 角色信息
     * @return 角色
     */
    Long createRole(RoleCreateVO reqVO);

    /**
     * 更新角色
     *
     * @param reqVO 角色信息
     */
    void updateRole(RoleUpdateVO reqVO);

    /**
     * 删除角色
     *
     * @param id id
     */
    void deleteRole(Long id);

    /**
     * 查询角色
     *
     * @param id id
     * @return 角色
     */
    RoleDO getRole(Long id);

    /**
     * 查询角色列表
     *
     * @param reqVO 请求
     * @return 角色列表
     */
    PageResult<RoleDO> getRolePage(RolePageVO reqVO);

    /**
     * 获取角色列表
     *
     * @param status 状态
     * @return 角色列表
     */
    List<RoleDO> getRoleList(Boolean status);

    /**
     * 获取角色名称列表
     *
     * @return 角色名称列表
     */
    List<LabelValue> getLabelValueList();
}
