package cn.wnhyang.coolGuard.decision.convert;

import cn.wnhyang.coolGuard.common.pojo.PageResult;
import cn.wnhyang.coolGuard.decision.entity.Field;
import cn.wnhyang.coolGuard.decision.vo.FieldVO;
import cn.wnhyang.coolGuard.decision.vo.InputFieldVO;
import cn.wnhyang.coolGuard.decision.vo.OutputFieldVO;
import cn.wnhyang.coolGuard.decision.vo.create.FieldCreateVO;
import cn.wnhyang.coolGuard.decision.vo.update.FieldUpdateVO;
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

    InputFieldVO convert2InputFieldVO(Field po);

    PageResult<FieldVO> convert(PageResult<Field> pageResult);

    List<FieldVO> convert(List<Field> list);

    OutputFieldVO convert2OutputFieldVO(Field field);
}
