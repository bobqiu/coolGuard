package cn.wnhyang.coolGuard.convert;

import cn.wnhyang.coolGuard.entity.FieldRef;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.FieldRefVO;
import cn.wnhyang.coolGuard.vo.create.FieldRefCreateVO;
import cn.wnhyang.coolGuard.vo.update.FieldRefUpdateVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 字段引用
 *
 * @author wnhyang
 * @since 2025/01/19
 */
@Mapper
public interface FieldRefConvert {

    FieldRefConvert INSTANCE = Mappers.getMapper(FieldRefConvert.class);

    FieldRef convert(FieldRefCreateVO createVO);

    FieldRef convert(FieldRefUpdateVO updateVO);

    FieldRefVO convert(FieldRef po);

    PageResult<FieldRefVO> convert(PageResult<FieldRef> pageResult);

    List<FieldRefVO> convert(List<FieldRef> list);
}
