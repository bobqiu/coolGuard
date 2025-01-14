package cn.wnhyang.coolGuard.system.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.wnhyang.coolGuard.log.core.dto.LogCreateReqDTO;
import cn.wnhyang.coolGuard.log.core.service.LogService;
import cn.wnhyang.coolGuard.pojo.CommonResult;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.system.convert.OperateLogConvert;
import cn.wnhyang.coolGuard.system.dto.OperateLogCreateDTO;
import cn.wnhyang.coolGuard.system.entity.OperateLogDO;
import cn.wnhyang.coolGuard.system.mapper.OperateLogMapper;
import cn.wnhyang.coolGuard.system.service.OperateLogService;
import cn.wnhyang.coolGuard.system.vo.operatelog.OperateLogPageVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static cn.wnhyang.coolGuard.pojo.CommonResult.success;
import static cn.wnhyang.coolGuard.system.entity.OperateLogDO.JAVA_METHOD_ARGS_MAX_LENGTH;
import static cn.wnhyang.coolGuard.system.entity.OperateLogDO.RESULT_MAX_LENGTH;


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

    /**
     * 记录操作日志
     *
     * @param createReqDTO 操作日志请求
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createOperateLog(OperateLogCreateDTO createReqDTO) {
        OperateLogDO logDO = OperateLogConvert.INSTANCE.convert(createReqDTO);
        logDO.setJavaMethodArgs(StrUtil.subPre(logDO.getJavaMethodArgs(), JAVA_METHOD_ARGS_MAX_LENGTH));
        logDO.setResultData(StrUtil.subPre(logDO.getResultData(), RESULT_MAX_LENGTH));
        operateLogMapper.insert(logDO);
    }

    @Override
    public PageResult<OperateLogDO> getOperateLogPage(OperateLogPageVO reqVO) {
        return operateLogMapper.selectPage(reqVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult<Boolean> createLog(LogCreateReqDTO reqDTO) {
        OperateLogCreateDTO operateLog = OperateLogConvert.INSTANCE.convert(reqDTO);
        createOperateLog(operateLog);
        return success(true);
    }
}
