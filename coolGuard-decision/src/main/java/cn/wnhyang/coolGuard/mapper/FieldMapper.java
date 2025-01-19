package cn.wnhyang.coolGuard.mapper;

import cn.wnhyang.coolGuard.constant.RedisKey;
import cn.wnhyang.coolGuard.entity.Field;
import cn.wnhyang.coolGuard.mybatis.BaseMapperX;
import cn.wnhyang.coolGuard.mybatis.LambdaQueryWrapperX;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.page.FieldPageVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.cache.annotation.Cacheable;

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

    @Cacheable(cacheNames = RedisKey.FIELD + "::co", key = "#code", unless = "#result == null")
    default Field selectByCode(String code) {
        return selectOne(Field::getCode, code);
    }

    default Long selectCountByFieldGroupCode(String groupCode) {
        return selectCount(Field::getCode, groupCode);
    }

}
