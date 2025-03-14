package cn.wnhyang.coolguard.decision.convert;

import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.decision.entity.Access;
import cn.wnhyang.coolguard.decision.vo.AccessVO;
import cn.wnhyang.coolguard.decision.vo.create.AccessCreateVO;
import cn.wnhyang.coolguard.decision.vo.update.AccessUpdateVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author wnhyang
 * @date 2024/3/14
 **/
@Mapper
public interface AccessConvert {

    AccessConvert INSTANCE = Mappers.getMapper(AccessConvert.class);

    Access convert(AccessCreateVO createVO);

    Access convert(AccessUpdateVO updateVO);

    AccessVO convert(Access po);

    PageResult<AccessVO> convert(PageResult<Access> pageResult);

    AccessCreateVO convert2Create(Access access);
}
