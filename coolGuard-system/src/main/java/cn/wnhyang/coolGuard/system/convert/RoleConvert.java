package cn.wnhyang.coolGuard.system.convert;


import cn.wnhyang.coolGuard.common.pojo.PageResult;
import cn.wnhyang.coolGuard.system.dto.RoleSimpleVO;
import cn.wnhyang.coolGuard.system.entity.RoleDO;
import cn.wnhyang.coolGuard.system.vo.role.RoleCreateVO;
import cn.wnhyang.coolGuard.system.vo.role.RoleRespVO;
import cn.wnhyang.coolGuard.system.vo.role.RoleUpdateVO;
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
