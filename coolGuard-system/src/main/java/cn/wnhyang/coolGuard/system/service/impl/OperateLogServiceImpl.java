package cn.wnhyang.coolGuard.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.wnhyang.coolGuard.log.core.dto.LogCreateReqDTO;
import cn.wnhyang.coolGuard.log.core.service.LogService;
import cn.wnhyang.coolGuard.pojo.CommonResult;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.system.convert.OperateLogConvert;
import cn.wnhyang.coolGuard.system.dto.OperateLogCreateDTO;
import cn.wnhyang.coolGuard.system.entity.OperateLog;
import cn.wnhyang.coolGuard.system.entity.User;
import cn.wnhyang.coolGuard.system.mapper.OperateLogMapper;
import cn.wnhyang.coolGuard.system.service.OperateLogService;
import cn.wnhyang.coolGuard.system.service.UserService;
import cn.wnhyang.coolGuard.system.vo.operatelog.OperateLogPageVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

import static cn.wnhyang.coolGuard.pojo.CommonResult.success;
import static cn.wnhyang.coolGuard.system.entity.OperateLog.JAVA_METHOD_ARGS_MAX_LENGTH;
import static cn.wnhyang.coolGuard.system.entity.OperateLog.RESULT_MAX_LENGTH;
import static cn.wnhyang.coolGuard.util.CollectionUtils.convertSet;


/**
 * 操作日志
 *
 * @author wnhyang
 * @since 2023/06/05
 */
@Service
@RequiredArgsConstructor
public class OperateLogServiceImpl implements OperateLogService, LogService {

    private final OperateLogMapper operateLogMapper;

    private final UserService userService;

    /**
     * 记录操作日志
     *
     * @param createReqDTO 操作日志请求
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createOperateLog(OperateLogCreateDTO createReqDTO) {
        OperateLog logDO = OperateLogConvert.INSTANCE.convert(createReqDTO);
        logDO.setJavaMethodArgs(StrUtil.subPre(logDO.getJavaMethodArgs(), JAVA_METHOD_ARGS_MAX_LENGTH));
        logDO.setResultData(StrUtil.subPre(logDO.getResultData(), RESULT_MAX_LENGTH));
        operateLogMapper.insert(logDO);
    }

    @Override
    public PageResult<OperateLog> getOperateLogPage(OperateLogPageVO reqVO) {
        // 处理基于用户昵称的查询
        Collection<Long> userIds = null;
        if (StrUtil.isNotEmpty(reqVO.getUserNickname())) {
            userIds = convertSet(userService.getUserListByNickname(reqVO.getUserNickname()), User::getId);
            if (CollUtil.isEmpty(userIds)) {
                return PageResult.empty();
            }
        }
        // 查询分页
        return operateLogMapper.selectPage(reqVO, userIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult<Boolean> createLog(LogCreateReqDTO reqDTO) {
        OperateLogCreateDTO operateLog = OperateLogConvert.INSTANCE.convert(reqDTO);
        createOperateLog(operateLog);
        return success(true);
    }
}
