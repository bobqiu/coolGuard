package cn.wnhyang.coolguard.system.service.impl;

import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.system.convert.LoginLogConvert;
import cn.wnhyang.coolguard.system.dto.LoginLogCreateDTO;
import cn.wnhyang.coolguard.system.entity.LoginLogDO;
import cn.wnhyang.coolguard.system.mapper.LoginLogMapper;
import cn.wnhyang.coolguard.system.service.LoginLogService;
import cn.wnhyang.coolguard.system.vo.loginlog.LoginLogPageVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 登录日志
 *
 * @author wnhyang
 * @since 2023/07/25
 */
@Service
@RequiredArgsConstructor
public class LoginLogServiceImpl implements LoginLogService {

    private final LoginLogMapper loginLogMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createLoginLog(LoginLogCreateDTO reqDTO) {
        LoginLogDO loginLogDO = LoginLogConvert.INSTANCE.convert(reqDTO);
        loginLogMapper.insert(loginLogDO);
    }

    @Override
    public PageResult<LoginLogDO> getLoginLogPage(LoginLogPageVO reqVO) {
        return loginLogMapper.selectPage(reqVO);
    }
}
