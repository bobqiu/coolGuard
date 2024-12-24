package cn.wnhyang.coolGuard.convert;

import cn.wnhyang.coolGuard.entity.FieldGroup;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.FieldGroupVO;
import cn.wnhyang.coolGuard.vo.create.FieldGroupCreateVO;
import cn.wnhyang.coolGuard.vo.update.FieldGroupUpdateVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author wnhyang
 * @date 2024/3/14
 **/
@Mapper
public interface FieldGroupConvert {

    FieldGroupConvert INSTANCE = Mappers.getMapper(FieldGroupConvert.class);

    FieldGroup convert(FieldGroupCreateVO createVO);

    FieldGroup convert(FieldGroupUpdateVO updateVO);

    FieldGroupVO convert(FieldGroup po);

    PageResult<FieldGroupVO> convert(PageResult<FieldGroup> pageResult);

    List<FieldGroupVO> convert(List<FieldGroup> fieldGroups);
}
