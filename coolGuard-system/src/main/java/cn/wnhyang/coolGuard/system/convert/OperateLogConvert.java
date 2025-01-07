package cn.wnhyang.coolGuard.system.convert;


import cn.wnhyang.coolGuard.log.core.dto.LogCreateReqDTO;
import cn.wnhyang.coolGuard.system.dto.OperateLogCreateDTO;
import cn.wnhyang.coolGuard.system.entity.OperateLog;
import cn.wnhyang.coolGuard.system.vo.operatelog.OperateLogVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author wnhyang
 * @date 2023/6/6
 **/
@Mapper
public interface OperateLogConvert {

    OperateLogConvert INSTANCE = Mappers.getMapper(OperateLogConvert.class);

    OperateLog convert(OperateLogCreateDTO reqDTO);

    OperateLogVO convert(OperateLog operateLog);

    OperateLogCreateDTO convert(LogCreateReqDTO reqDTO);
}
