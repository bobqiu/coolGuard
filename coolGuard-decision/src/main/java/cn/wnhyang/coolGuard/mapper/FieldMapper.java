package cn.wnhyang.coolGuard.mapper;

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
        return selectPage(pageVO, new LambdaQueryWrapperX<Field>());
    }

    @Cacheable(cacheNames = "fieldByName", key = "#name", unless = "#result == null")
    default Field selectByName(String name) {
        return selectOne(Field::getName, name);
    }
}
