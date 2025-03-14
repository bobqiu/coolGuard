package cn.wnhyang.coolguard.system.convert;


import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.system.entity.RsaDO;
import cn.wnhyang.coolguard.system.vo.rsa.RsaCreateVO;
import cn.wnhyang.coolguard.system.vo.rsa.RsaRespVO;
import cn.wnhyang.coolguard.system.vo.rsa.RsaUpdateVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author wnhyang
 * @date 2023/10/10
 **/
@Mapper
public interface RsaConvert {
    RsaConvert INSTANCE = Mappers.getMapper(RsaConvert.class);

    RsaDO convert(RsaCreateVO reqVO);

    RsaDO convert(RsaUpdateVO reqVO);

    PageResult<RsaRespVO> convertPage(PageResult<RsaDO> page);

    RsaRespVO convert(RsaDO rsa);
}
