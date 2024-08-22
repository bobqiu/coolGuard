package cn.wnhyang.coolGuard.system.convert;


import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.system.entity.RsaPO;
import cn.wnhyang.coolGuard.system.vo.rsa.RsaCreateVO;
import cn.wnhyang.coolGuard.system.vo.rsa.RsaRespVO;
import cn.wnhyang.coolGuard.system.vo.rsa.RsaUpdateVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author wnhyang
 * @date 2023/10/10
 **/
@Mapper
public interface RsaConvert {
    RsaConvert INSTANCE = Mappers.getMapper(RsaConvert.class);

    RsaPO convert(RsaCreateVO reqVO);

    RsaPO convert(RsaUpdateVO reqVO);

    PageResult<RsaRespVO> convertPage(PageResult<RsaPO> page);

    RsaRespVO convert(RsaPO rsa);
}
