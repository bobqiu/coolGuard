package cn.wnhyang.coolGuard.system.convert;


import cn.wnhyang.coolGuard.system.entity.Menu;
import cn.wnhyang.coolGuard.system.vo.menu.*;
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

    Menu convert(MenuCreateVO reqVO);

    Menu convert(MenuUpdateVO reqVO);

    MenuRespVO convert2RespVO(Menu menu);

    List<MenuRespVO> convertList(List<Menu> list);

    List<MenuSimpleTreeVO> convert02(List<Menu> list);

    List<MenuTreeRespVO> convert2TreeRespList(List<Menu> menus);

}
