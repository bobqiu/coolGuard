package cn.wnhyang.coolguard.system.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.wnhyang.coolguard.common.pojo.CommonResult;
import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.log.dto.LogCreateReqDTO;
import cn.wnhyang.coolguard.log.service.LogService;
import cn.wnhyang.coolguard.system.convert.OperateLogConvert;
import cn.wnhyang.coolguard.system.dto.OperateLogCreateDTO;
import cn.wnhyang.coolguard.system.entity.OperateLogDO;
import cn.wnhyang.coolguard.system.mapper.OperateLogMapper;
import cn.wnhyang.coolguard.system.service.OperateLogService;
import cn.wnhyang.coolguard.system.vo.operatelog.OperateLogPageVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static cn.wnhyang.coolguard.common.pojo.CommonResult.success;


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
        logDO.setRequestParams(StrUtil.maxLength(logDO.getRequestParams(), OperateLogDO.REQUEST_PARAMS_MAX_LENGTH));
        logDO.setResultMsg(StrUtil.maxLength(logDO.getResultMsg(), OperateLogDO.RESULT_MSG_MAX_LENGTH));
        logDO.setResultData(StrUtil.maxLength(logDO.getResultData(), OperateLogDO.RESULT_DATA_MAX_LENGTH));
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
