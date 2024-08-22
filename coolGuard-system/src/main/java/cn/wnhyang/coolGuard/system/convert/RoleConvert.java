package cn.wnhyang.coolGuard.system.convert;


import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.system.dto.RoleSimpleVO;
import cn.wnhyang.coolGuard.system.entity.RolePO;
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

    RolePO convert(RoleCreateVO reqVO);

    RolePO convert(RoleUpdateVO reqVO);

    RoleRespVO convert(RolePO role);

    PageResult<RoleRespVO> convert(PageResult<RolePO> pageResult);

    List<RoleSimpleVO> convert02(List<RolePO> roleList);
}
