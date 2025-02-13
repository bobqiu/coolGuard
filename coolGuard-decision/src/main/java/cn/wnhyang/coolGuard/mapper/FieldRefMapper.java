package cn.wnhyang.coolGuard.mapper;

import cn.wnhyang.coolGuard.entity.FieldRef;
import cn.wnhyang.coolGuard.mybatis.BaseMapperX;
import cn.wnhyang.coolGuard.mybatis.LambdaQueryWrapperX;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.page.FieldRefPageVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 字段引用 Mapper 接口
 *
 * @author wnhyang
 * @since 2025/01/19
 */
@Mapper
public interface FieldRefMapper extends BaseMapperX<FieldRef> {

    default PageResult<FieldRef> selectPage(FieldRefPageVO pageVO) {
        return selectPage(pageVO, new LambdaQueryWrapperX<FieldRef>()
                .eqIfPresent(FieldRef::getRefType, pageVO.getRefType())
                .eqIfPresent(FieldRef::getRefBy, pageVO.getRefBy())
                .eqIfPresent(FieldRef::getRefSubType, pageVO.getRefSubType()));
    }

    default List<FieldRef> selectListByRef(String refType, String refBy, String refSubType) {
        return selectList(new LambdaQueryWrapperX<FieldRef>()
                .eqIfPresent(FieldRef::getRefType, refType)
                .eqIfPresent(FieldRef::getRefBy, refBy)
                .eqIfPresent(FieldRef::getRefSubType, refSubType));
    }

    default List<FieldRef> selectList(FieldRefPageVO pageVO) {
        return selectList(new LambdaQueryWrapperX<FieldRef>()
                .eqIfPresent(FieldRef::getRefType, pageVO.getRefType())
                .eqIfPresent(FieldRef::getRefBy, pageVO.getRefBy())
                .eqIfPresent(FieldRef::getRefSubType, pageVO.getRefSubType()));
    }
}
