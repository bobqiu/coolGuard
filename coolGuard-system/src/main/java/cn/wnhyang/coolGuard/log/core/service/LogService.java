package cn.wnhyang.coolGuard.log.core.service;


import cn.wnhyang.coolGuard.log.core.dto.LogCreateReqDTO;
import cn.wnhyang.coolGuard.pojo.CommonResult;

/**
 * @author wnhyang
 * @date 2024/1/5
 **/
public interface LogService {

    /**
     * 创建日志
     *
     * @param reqDTO 日志创建请求
     * @return 日志创建结果
     */
    CommonResult<Boolean> createLog(LogCreateReqDTO reqDTO);
}
