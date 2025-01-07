package cn.wnhyang.coolGuard.system.service.impl;

import cn.dev33.satoken.secure.BCrypt;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.wnhyang.coolGuard.entity.LabelValue;
import cn.wnhyang.coolGuard.enums.UserConstants;
import cn.wnhyang.coolGuard.mybatis.LambdaQueryWrapperX;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.satoken.core.util.LoginUtil;
import cn.wnhyang.coolGuard.system.convert.UserConvert;
import cn.wnhyang.coolGuard.system.dto.UserCreateDTO;
import cn.wnhyang.coolGuard.system.entity.Role;
import cn.wnhyang.coolGuard.system.entity.User;
import cn.wnhyang.coolGuard.system.entity.UserRole;
import cn.wnhyang.coolGuard.system.login.LoginUser;
import cn.wnhyang.coolGuard.system.mapper.RoleMapper;
import cn.wnhyang.coolGuard.system.mapper.UserMapper;
import cn.wnhyang.coolGuard.system.mapper.UserRoleMapper;
import cn.wnhyang.coolGuard.system.service.PermissionService;
import cn.wnhyang.coolGuard.system.service.UserService;
import cn.wnhyang.coolGuard.system.vo.user.*;
import cn.wnhyang.coolGuard.system.vo.userprofile.UserProfileUpdatePasswordVO;
import cn.wnhyang.coolGuard.system.vo.userprofile.UserProfileUpdateVO;
import cn.wnhyang.coolGuard.util.CollectionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

import static cn.wnhyang.coolGuard.exception.util.ServiceExceptionUtil.exception;
import static cn.wnhyang.coolGuard.system.enums.ErrorCodes.*;


/**
 * 用户
 *
 * @author wnhyang
 * @since 2023/05/14
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PermissionService permissionService;

    private final UserMapper userMapper;

    private final UserRoleMapper userRoleMapper;

    private final RoleMapper roleMapper;

    @Override
    public User getUserByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUserLogin(Long userId, String loginIp) {
        userMapper.updateById(new User().setId(userId).setLoginIp(loginIp).setLoginDate(LocalDateTime.now()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void registerUser(UserCreateDTO reqDTO) {
        validateUserForCreateOrUpdate(null, reqDTO.getUsername(), reqDTO.getMobile(), reqDTO.getEmail());
        userMapper.insert(UserConvert.INSTANCE.convert(reqDTO));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createUser(UserCreateVO reqVO) {
        validateUserForCreateOrUpdate(null, reqVO.getUsername(), reqVO.getMobile(), reqVO.getEmail());
        User user = UserConvert.INSTANCE.convert(reqVO);
        // TODO，新建用户，初始密码默认或者通过邮箱发送
        user.setPassword(BCrypt.hashpw(UserConstants.DEFAULT_PASSWORD));
        userMapper.insert(user);
        if (CollectionUtil.isNotEmpty(reqVO.getRoleIds())) {
            userRoleMapper.insertBatch(CollectionUtils.convertList(reqVO.getRoleIds(),
                    roleId -> new UserRole().setUserId(user.getId()).setRoleId(roleId)));
        }
        return user.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(UserUpdateVO reqVO) {
        validateUserForCreateOrUpdate(reqVO.getId(), reqVO.getUsername(), reqVO.getMobile(), reqVO.getEmail());
        User convert = UserConvert.INSTANCE.convert(reqVO);
        userRoleMapper.deleteByUserId(convert.getId());
        // TODO 不能分配超级管理员角色
        if (CollectionUtil.isNotEmpty(reqVO.getRoleIds())) {
            userRoleMapper.insertBatch(CollectionUtils.convertList(reqVO.getRoleIds(),
                    roleId -> new UserRole().setUserId(convert.getId()).setRoleId(roleId)));
        }
        userMapper.updateById(convert);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(Long id) {
        validateUserExists(id);
        userMapper.deleteById(id);
        userRoleMapper.deleteByUserId(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUserPassword(UserUpdatePasswordVO reqVO) {
        validateUserExists(reqVO.getId());
        userMapper.updateById(new User().setId(reqVO.getId()).setPassword(BCrypt.hashpw(reqVO.getPassword())));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUserStatus(UserUpdateStatusVO reqVO) {
        validateUserExists(reqVO.getId());
        userMapper.updateById(new User().setId(reqVO.getId()).setStatus(reqVO.getStatus()));
    }

    @Override
    public LoginUser getLoginUser(String username, String mobile, String email) {
        LambdaQueryWrapperX<User> wrapperX = new LambdaQueryWrapperX<>();
        wrapperX.eqIfPresent(User::getUsername, username);
        if (StrUtil.isNotEmpty(mobile)) {
            wrapperX.or().eq(User::getMobile, mobile);
        }
        if (StrUtil.isNotEmpty(email)) {
            wrapperX.or().eq(User::getEmail, email);
        }
        User user = userMapper.selectOne(wrapperX);
        if (user == null) {
            throw exception(USER_BAD_CREDENTIALS);
        }
        return buildLoginUser(user);
    }

    @Override
    public User getUserById(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    public PageResult<User> getUserPage(UserPageVO reqVO) {
        return userMapper.selectPage(reqVO);
    }

    @Override
    public List<User> getUserListByNickname(String nickname) {
        return userMapper.selectListByNickname(nickname);
    }

    @Override
    public List<User> getUserList(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return Collections.emptyList();
        }
        return userMapper.selectByIds(ids);
    }

    @Override
    public void updateUserPassword(Long id, UserProfileUpdatePasswordVO reqVO) {
        validateUserExists(id);
        validateOldPassword(id, reqVO.getOldPassword());
        // 执行更新
        userMapper.updateById(new User().setId(id).setPassword(BCrypt.hashpw(reqVO.getNewPassword())));
    }

    @Override
    public void updateUserProfile(Long id, UserProfileUpdateVO reqVO) {
        validateUserExists(id);
        validateMobileUnique(id, reqVO.getMobile());
        validateEmailUnique(id, reqVO.getEmail());
        // 执行更新
        userMapper.updateById(UserConvert.INSTANCE.convert(reqVO).setId(id));
    }

    /**
     * 校验旧密码
     *
     * @param id          用户 id
     * @param oldPassword 旧密码
     */
    void validateOldPassword(Long id, String oldPassword) {
        User user = userMapper.selectById(id);
        if (user == null) {
            throw exception(USER_NOT_EXISTS);
        }
        if (!BCrypt.checkpw(oldPassword, user.getPassword())) {
            throw exception(USER_PASSWORD_FAILED);
        }
    }

    private LoginUser buildLoginUser(User user) {
        LoginUser loginUser = UserConvert.INSTANCE.convert(user);
        Set<Long> roleIds = permissionService.getRoleIdListByUserId(loginUser.getId());
        List<Role> roleList = roleMapper.selectByIds(roleIds);
        List<LabelValue> roles = CollectionUtils.convertList(roleList, Role::getLabelValue);
        loginUser.setRoles(roles);
        Set<String> perms = new HashSet<>();
        if (LoginUtil.isAdministrator(loginUser.getId())) {
            perms.add("*:*:*");
        } else {
            perms.addAll(permissionService.getPermissionsByRoleIds(roleIds));
        }
        loginUser.setPermissions(perms);
        return loginUser;
    }


    private void validateUserForCreateOrUpdate(Long id, String username, String mobile, String email) {
        // 校验用户存在
        validateUserExists(id);
        // 校验用户名唯一
        validateUsernameUnique(id, username);
        // 校验手机号唯一
        validateMobileUnique(id, mobile);
        // 校验邮箱唯一
        validateEmailUnique(id, email);
    }

    private void validateUserExists(Long id) {
        if (id == null) {
            return;
        }
        User user = userMapper.selectById(id);
        if (user == null) {
            throw exception(USER_NOT_EXISTS);
        }
    }

    private void validateUsernameUnique(Long id, String username) {
        if (StrUtil.isBlank(username)) {
            return;
        }
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的用户
        if (id == null) {
            throw exception(USER_USERNAME_EXISTS);
        }
        if (!user.getId().equals(id)) {
            throw exception(USER_USERNAME_EXISTS);
        }
    }

    private void validateMobileUnique(Long id, String mobile) {
        if (StrUtil.isBlank(mobile)) {
            return;
        }
        User user = userMapper.selectByMobile(mobile);
        if (user == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的用户
        if (id == null) {
            throw exception(USER_MOBILE_EXISTS);
        }
        if (!user.getId().equals(id)) {
            throw exception(USER_MOBILE_EXISTS);
        }
    }

    private void validateEmailUnique(Long id, String email) {
        if (StrUtil.isBlank(email)) {
            return;
        }
        User user = userMapper.selectByEmail(email);
        if (user == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的用户
        if (id == null) {
            throw exception(USER_EMAIL_EXISTS);
        }
        if (!user.getId().equals(id)) {
            throw exception(USER_EMAIL_EXISTS);
        }
    }

}
