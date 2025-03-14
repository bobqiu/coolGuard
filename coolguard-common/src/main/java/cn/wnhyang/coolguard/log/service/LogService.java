package cn.wnhyang.coolguard.log.service;


import cn.wnhyang.coolguard.common.pojo.CommonResult;
import cn.wnhyang.coolguard.log.dto.LogCreateReqDTO;

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
