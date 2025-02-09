package cn.wnhyang.coolGuard.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.wnhyang.coolGuard.satoken.Login;
import cn.wnhyang.coolGuard.satoken.core.util.LoginUtil;
import cn.wnhyang.coolGuard.system.convert.MenuConvert;
import cn.wnhyang.coolGuard.system.entity.MenuDO;
import cn.wnhyang.coolGuard.system.entity.RoleMenuDO;
import cn.wnhyang.coolGuard.system.enums.permission.MenuType;
import cn.wnhyang.coolGuard.system.mapper.MenuMapper;
import cn.wnhyang.coolGuard.system.mapper.RoleMenuMapper;
import cn.wnhyang.coolGuard.system.service.MenuService;
import cn.wnhyang.coolGuard.system.vo.menu.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static cn.wnhyang.coolGuard.exception.GlobalErrorCode.UNAUTHORIZED;
import static cn.wnhyang.coolGuard.exception.util.ServiceExceptionUtil.exception;
import static cn.wnhyang.coolGuard.system.entity.MenuDO.ID_ROOT;
import static cn.wnhyang.coolGuard.system.enums.ErrorCodes.*;
import static cn.wnhyang.coolGuard.util.CollectionUtils.convertSet;


/**
 * 菜单
 *
 * @author wnhyang
 * @since 2023/05/14
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuMapper menuMapper;

    private final RoleMenuMapper roleMenuMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createMenu(MenuCreateVO reqVO) {
        // 校验父菜单存在
        validateParentMenu(reqVO.getParentId(), null);
        // 校验菜单（自己）
        validateMenuNameUnique(reqVO.getName(), null);
        validateMenuPathUnique(reqVO.getPath(), null);

        // 插入数据库
        MenuDO menuDO = MenuConvert.INSTANCE.convert(reqVO);
        initMenuProperty(menuDO);
        menuMapper.insert(menuDO);
        // 返回
        return menuDO.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMenu(MenuUpdateVO reqVO) {
        // 校验更新的菜单是否存在
        if (menuMapper.selectById(reqVO.getId()) == null) {
            throw exception(MENU_NOT_EXISTS);
        }
        // 校验父菜单存在
        validateParentMenu(reqVO.getParentId(), reqVO.getId());
        // 校验菜单（自己）
        validateMenuNameUnique(reqVO.getName(), reqVO.getId());
        validateMenuPathUnique(reqVO.getPath(), reqVO.getId());

        // 更新到数据库
        MenuDO updateObject = MenuConvert.INSTANCE.convert(reqVO);
        initMenuProperty(updateObject);
        menuMapper.updateById(updateObject);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMenu(Long id) {
        // 校验删除的菜单是否存在
        if (menuMapper.selectById(id) == null) {
            throw exception(MENU_NOT_EXISTS);
        }
        // 校验是否还有子菜单
        if (menuMapper.selectCountByParentId(id) > 0) {
            throw exception(MENU_EXISTS_CHILDREN);
        }
        if (roleMenuMapper.selectCountByMenuId(id) > 0) {
            throw exception(MENU_HAS_ROLE);
        }
        // 标记删除
        menuMapper.deleteById(id);
    }

    @Override
    public List<MenuDO> getMenuList(MenuListVO reqVO) {
        return menuMapper.selectListOrder(reqVO);
    }

    @Override
    public MenuDO getMenu(Long id) {
        return menuMapper.selectById(id);
    }

    @Override
    public List<MenuDO> getMenuList(Set<Long> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            return Collections.emptyList();
        }
        return menuMapper.selectByIds(ids);
    }

    @Override
    public List<MenuDO> getMenuList() {
        return menuMapper.selectListOrder();
    }

    @Override
    public List<MenuTreeRespVO> getMenuTreeList(MenuListVO reqVO) {

        // 1、查询所有菜单
        List<MenuDO> all = menuMapper.selectListOrder();

        // 2、查询满足条件的菜单
        List<MenuDO> menuDOS = menuMapper.selectListOrder(reqVO);
        Set<Long> menuIds = menuDOS.stream().map(MenuDO::getId).collect(Collectors.toSet());

        Set<MenuDO> menuDOSet = findMenusWithParentsOrChildrenByIds(all, menuIds, true, true);

        // 3、形成树形结合
        return buildMenuTree(new ArrayList<>(menuDOSet), false);
    }

    @Override
    public List<MenuTreeRespVO> getLoginUserMenuWithRouteMetaTreeList(boolean removeButton) {
        Login loginUser = LoginUtil.getLoginUser();

        if (loginUser == null) {
            throw exception(UNAUTHORIZED);
        }
        Long id = loginUser.getId();

        List<MenuDO> all = menuMapper.selectListOrder();
        if (LoginUtil.isAdministrator(id)) {
            return buildMenuTree(all, removeButton);
        }
        Set<Long> menuIds = convertSet(roleMenuMapper.selectListByRoleId(loginUser.getRoleIds()), RoleMenuDO::getMenuId);
        Set<MenuDO> menuDOSet = findMenusWithParentsOrChildrenByIds(all, menuIds, true, false);

        return buildMenuTree(new ArrayList<>(menuDOSet), removeButton);
    }

    public List<MenuTreeRespVO> buildMenuTree(List<MenuDO> menuDOList, boolean removeButton) {

        if (removeButton) {
            // 移除按钮
            menuDOList.removeIf(menu -> menu.getType().equals(MenuType.BUTTON.getType()));
        }

        List<MenuTreeRespVO> convert = MenuConvert.INSTANCE.convert2TreeRespList(menuDOList);

        Map<Long, MenuTreeRespVO> menuTreeMap = new LinkedHashMap<>();
        for (MenuTreeRespVO menu : convert) {
            menuTreeMap.put(menu.getId(), menu);
        }

        menuTreeMap.values().stream().filter(menu -> !ID_ROOT.equals(menu.getParentId())).forEach(childMenu -> {
                    MenuTreeRespVO parentMenu = menuTreeMap.get(childMenu.getParentId());
                    if (parentMenu == null) {
                        log.info("id:{} 找不到父菜单 parentId:{}", childMenu.getId(), childMenu.getParentId());
                        return;
                    }
                    // 将自己添加到父节点中
                    if (parentMenu.getChildren() == null) {
                        parentMenu.setChildren(new ArrayList<>());
                    }
                    parentMenu.getChildren().add(childMenu);
                }

        );

        return menuTreeMap.values().stream().filter(menu -> ID_ROOT.equals(menu.getParentId())).collect(Collectors.toList());
    }

    public List<MenuSimpleTreeVO> buildMenuSimpleTree(List<MenuDO> menuDOList, boolean removeButton) {

        if (removeButton) {
            // 移除按钮
            menuDOList.removeIf(menu -> menu.getType().equals(MenuType.BUTTON.getType()));
        }

        List<MenuSimpleTreeVO> convert = MenuConvert.INSTANCE.convert02(menuDOList);

        Map<Long, MenuSimpleTreeVO> menuTreeMap = new LinkedHashMap<>();
        for (MenuSimpleTreeVO menu : convert) {
            menuTreeMap.put(menu.getId(), menu);
        }

        menuTreeMap.values().stream().filter(menu -> !ID_ROOT.equals(menu.getParentId())).forEach(childMenu -> {
                    MenuSimpleTreeVO parentMenu = menuTreeMap.get(childMenu.getParentId());
                    if (parentMenu == null) {
                        log.info("id:{} 找不到父菜单 parentId:{}", childMenu.getId(), childMenu.getParentId());
                        return;
                    }
                    // 将自己添加到父节点中
                    if (parentMenu.getChildren() == null) {
                        parentMenu.setChildren(new ArrayList<>());
                    }
                    parentMenu.getChildren().add(childMenu);
                }

        );

        return menuTreeMap.values().stream().filter(menu -> ID_ROOT.equals(menu.getParentId())).collect(Collectors.toList());
    }

    /**
     * 查找菜单的父/子菜单集合
     *
     * @param all          所有菜单
     * @param menuIds      需要的菜单集合
     * @param withParent   是否包含父菜单
     * @param withChildren 是否包含子菜单
     * @return 结果
     */
    private Set<MenuDO> findMenusWithParentsOrChildrenByIds(List<MenuDO> all, Set<Long> menuIds, boolean withParent, boolean withChildren) {
        Map<Long, MenuDO> menuMap = new HashMap<>();
        for (MenuDO menuDO : all) {
            menuMap.put(menuDO.getId(), menuDO);
        }

        // 使用LinkedHashSet保持插入顺序
        Set<MenuDO> result = new LinkedHashSet<>();
        // 存储已处理过的菜单ID
        Set<Long> processedIds = new HashSet<>();
        for (Long menuId : menuIds) {
            if (withParent) {
                collectMenuParents(result, menuMap, menuId, processedIds);
            }
            if (withChildren) {
                collectMenuChildren(result, menuMap, menuId);
            }
        }

        return result;
    }

    /**
     * 递归查找当前菜单的所有父菜单
     *
     * @param resultSet    结果
     * @param menuMap      menuMap
     * @param menuId       需要的菜单id
     * @param processedIds 存储已处理过的菜单id
     */
    private void collectMenuParents(Set<MenuDO> resultSet, Map<Long, MenuDO> menuMap, Long menuId, Set<Long> processedIds) {
        if (processedIds.contains(menuId)) {
            return; // 如果已经处理过此菜单，则不再处理
        }

        processedIds.add(menuId);
        MenuDO menuDO = menuMap.get(menuId);
        if (menuDO != null) {
            resultSet.add(menuDO);

            // 如果当前菜单不是根节点（即parentId不为0），继续查找其父菜单
            if (!Objects.equals(menuDO.getParentId(), ID_ROOT) && !processedIds.contains(menuDO.getParentId())) {
                collectMenuParents(resultSet, menuMap, menuDO.getParentId(), processedIds);
            }
        }
    }

    /**
     * 递归查找当前菜单的所有子菜单
     *
     * @param resultSet 结果
     * @param menuMap   menuMap
     * @param menuId    需要的菜单id
     */
    private void collectMenuChildren(Set<MenuDO> resultSet, Map<Long, MenuDO> menuMap, Long menuId) {
        MenuDO menuDO = menuMap.get(menuId);
        if (menuDO != null) {
            resultSet.add(menuDO);

            // 添加当前菜单的所有子菜单
            for (MenuDO child : menuMap.values()) {
                if (child.getParentId().equals(menuDO.getId())) {
                    collectMenuChildren(resultSet, menuMap, child.getId());
                }
            }
        }
    }

    @Override
    public List<MenuSimpleTreeVO> getMenuSimpleTreeList() {
        return getMenuSimpleTreeList(false);
    }

    @Override
    public List<MenuSimpleTreeVO> getMenuSimpleTreeListA() {
        return getMenuSimpleTreeList(true);
    }

    private List<MenuSimpleTreeVO> getMenuSimpleTreeList(boolean withRoot) {
        List<MenuDO> all = menuMapper.selectListOrder();

        List<MenuSimpleTreeVO> menuSimpleTreeList = buildMenuSimpleTree(all, false);

        if (withRoot) {
            MenuSimpleTreeVO result = new MenuSimpleTreeVO();
            result.setId(ID_ROOT).setTitle("根目录").setChildren(menuSimpleTreeList);
            return Collections.singletonList(result);
        }

        // 返回result
        return menuSimpleTreeList;
    }

    /**
     * 校验父菜单是否合法
     * <p>
     * 1. 不能设置自己为父菜单
     * 2. 父菜单不存在
     * 3. 父菜单必须是 {@link MenuType#MENU} 菜单类型
     *
     * @param parentId 父菜单编号
     * @param childId  当前菜单编号
     */
    void validateParentMenu(Long parentId, Long childId) {
        if (parentId == null || ID_ROOT.equals(parentId)) {
            return;
        }
        // 不能设置自己为父菜单
        if (parentId.equals(childId)) {
            throw exception(MENU_PARENT_ERROR);
        }
        MenuDO menuDO = menuMapper.selectById(parentId);
        // 父菜单不存在
        if (menuDO == null) {
            throw exception(MENU_PARENT_NOT_EXISTS);
        }
        // 父菜单必须是目录或者菜单类型
        if (!MenuType.DIR.getType().equals(menuDO.getType())
                && !MenuType.MENU.getType().equals(menuDO.getType())) {
            throw exception(MENU_PARENT_NOT_DIR_OR_MENU);
        }
    }

    void validateMenuNameUnique(String name, Long id) {
        MenuDO menuDO = menuMapper.selectByName(name);
        if (menuDO == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的菜单
        if (id == null) {
            throw exception(MENU_NAME_EXISTS);
        }
        if (!menuDO.getId().equals(id)) {
            throw exception(MENU_NAME_EXISTS);
        }
    }

    void validateMenuPathUnique(String path, Long id) {
        MenuDO menuDO = menuMapper.selectByPath(path);
        if (menuDO == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的菜单
        if (id == null) {
            throw exception(MENU_PATH_EXISTS);
        }
        if (!menuDO.getId().equals(id)) {
            throw exception(MENU_PATH_EXISTS);
        }
    }

    /**
     * 初始化菜单的通用属性。
     * <p>
     * 例如说，只有目录或者菜单类型的菜单，才设置 icon
     *
     * @param menuDO 菜单
     */
    private void initMenuProperty(MenuDO menuDO) {
        // 菜单为按钮类型时，无需 component、icon、path 属性，进行置空
        if (MenuType.BUTTON.getType().equals(menuDO.getType())) {
            menuDO.setComponent("");
            menuDO.setPath("");
        }
    }
}
