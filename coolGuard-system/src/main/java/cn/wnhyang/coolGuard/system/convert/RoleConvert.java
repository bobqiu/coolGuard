package cn.wnhyang.coolGuard.system.convert;


import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.system.dto.RoleSimpleVO;
import cn.wnhyang.coolGuard.system.entity.Role;
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

    Role convert(RoleCreateVO reqVO);

    Role convert(RoleUpdateVO reqVO);

    RoleRespVO convert(Role role);

    PageResult<RoleRespVO> convert(PageResult<Role> pageResult);

    List<RoleSimpleVO> convert02(List<Role> roleList);
}
