package cn.wnhyang.coolguard.decision.convert;

import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.decision.entity.Field;
import cn.wnhyang.coolguard.decision.vo.FieldVO;
import cn.wnhyang.coolguard.decision.vo.create.FieldCreateVO;
import cn.wnhyang.coolguard.decision.vo.update.FieldUpdateVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author wnhyang
 * @date 2024/3/14
 **/
@Mapper
public interface FieldConvert {

    FieldConvert INSTANCE = Mappers.getMapper(FieldConvert.class);

    Field convert(FieldCreateVO createVO);

    Field convert(FieldUpdateVO updateVO);

    FieldVO convert(Field po);

    PageResult<FieldVO> convert(PageResult<Field> pageResult);

    List<FieldVO> convert(List<Field> list);

}
