package cn.wnhyang.coolguard.decision.mapper;

import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.decision.entity.FieldGroup;
import cn.wnhyang.coolguard.decision.vo.page.FieldGroupPageVO;
import cn.wnhyang.coolguard.mybatis.mapper.BaseMapperX;
import cn.wnhyang.coolguard.mybatis.wrapper.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

/**
 * 字段分组表 Mapper 接口
 *
 * @author wnhyang
 * @since 2024/03/14
 */
@Mapper
public interface FieldGroupMapper extends BaseMapperX<FieldGroup> {

    default PageResult<FieldGroup> selectPage(FieldGroupPageVO pageVO) {
        return selectPage(pageVO, new LambdaQueryWrapperX<FieldGroup>()
                .likeIfPresent(FieldGroup::getName, pageVO.getName())
                .likeIfPresent(FieldGroup::getCode, pageVO.getCode()));
    }

    default FieldGroup selectByCode(String code) {
        return selectOne(FieldGroup::getCode, code);
    }
}
