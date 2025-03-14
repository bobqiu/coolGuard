package cn.wnhyang.coolguard.system.convert;


import cn.wnhyang.coolguard.system.entity.MenuDO;
import cn.wnhyang.coolguard.system.vo.menu.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;


/**
 * @author wnhyang
 * @date 2023/8/14
 **/
@Mapper
public interface MenuConvert {

    MenuConvert INSTANCE = Mappers.getMapper(MenuConvert.class);

    MenuDO convert(MenuCreateVO reqVO);

    MenuDO convert(MenuUpdateVO reqVO);

    MenuRespVO convert2RespVO(MenuDO menuDO);

    List<MenuRespVO> convertList(List<MenuDO> list);

    List<MenuSimpleTreeVO> convert02(List<MenuDO> list);

    List<MenuTreeRespVO> convert2TreeRespList(List<MenuDO> menuDOS);

}
