package cn.wnhyang.coolguard.system.convert;


import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.system.dto.RoleSimpleVO;
import cn.wnhyang.coolguard.system.entity.RoleDO;
import cn.wnhyang.coolguard.system.vo.role.RoleCreateVO;
import cn.wnhyang.coolguard.system.vo.role.RoleRespVO;
import cn.wnhyang.coolguard.system.vo.role.RoleUpdateVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author wnhyang
 * @date 2023/8/10
 **/
@Mapper
public interface RoleConvert {
    RoleConvert INSTANCE = Mappers.getMapper(RoleConvert.class);

    RoleDO convert(RoleCreateVO reqVO);

    RoleDO convert(RoleUpdateVO reqVO);

    RoleRespVO convert(RoleDO roleDO);

    PageResult<RoleRespVO> convert(PageResult<RoleDO> pageResult);

    List<RoleSimpleVO> convert02(List<RoleDO> roleDOList);
}
