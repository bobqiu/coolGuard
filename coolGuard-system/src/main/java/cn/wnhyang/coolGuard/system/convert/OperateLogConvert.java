package cn.wnhyang.coolGuard.system.convert;


import cn.wnhyang.coolGuard.common.pojo.PageResult;
import cn.wnhyang.coolGuard.log.dto.LogCreateReqDTO;
import cn.wnhyang.coolGuard.system.dto.OperateLogCreateDTO;
import cn.wnhyang.coolGuard.system.entity.OperateLogDO;
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

    OperateLogDO convert(OperateLogCreateDTO reqDTO);

    OperateLogVO convert(OperateLogDO operateLogDO);

    OperateLogCreateDTO convert(LogCreateReqDTO reqDTO);

    PageResult<OperateLogVO> convert(PageResult<OperateLogDO> pageResult);
}
