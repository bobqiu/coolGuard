package cn.wnhyang.coolguard.decision.mapper;

import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.decision.entity.Field;
import cn.wnhyang.coolguard.decision.vo.page.FieldPageVO;
import cn.wnhyang.coolguard.mybatis.mapper.BaseMapperX;
import cn.wnhyang.coolguard.mybatis.wrapper.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

/**
 * 字段表 Mapper 接口
 *
 * @author wnhyang
 * @since 2024/03/14
 */
@Mapper
public interface FieldMapper extends BaseMapperX<Field> {

    default PageResult<Field> selectPage(FieldPageVO pageVO) {
        return selectPage(pageVO, new LambdaQueryWrapperX<Field>()
                .likeIfPresent(Field::getCode, pageVO.getCode())
                .likeIfPresent(Field::getName, pageVO.getName())
                .eqIfPresent(Field::getGroupCode, pageVO.getGroupCode())
                .eqIfPresent(Field::getDynamic, pageVO.getDynamic())
                .eqIfPresent(Field::getType, pageVO.getType())
                .eqIfPresent(Field::getStandard, pageVO.getStandard()));
    }

    default Field selectByCode(String code) {
        return selectOne(Field::getCode, code);
    }

    default Long selectCountByFieldGroupCode(String groupCode) {
        return selectCount(Field::getGroupCode, groupCode);
    }

}
