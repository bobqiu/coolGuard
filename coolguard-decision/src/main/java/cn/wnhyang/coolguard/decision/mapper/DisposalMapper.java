package cn.wnhyang.coolguard.decision.mapper;

import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.decision.entity.Disposal;
import cn.wnhyang.coolguard.decision.vo.page.DisposalPageVO;
import cn.wnhyang.coolguard.mybatis.mapper.BaseMapperX;
import cn.wnhyang.coolguard.mybatis.wrapper.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

/**
 * 处置表 Mapper 接口
 *
 * @author wnhyang
 * @since 2024/04/03
 */
@Mapper
public interface DisposalMapper extends BaseMapperX<Disposal> {

    default PageResult<Disposal> selectPage(DisposalPageVO pageVO) {
        return selectPage(pageVO, new LambdaQueryWrapperX<Disposal>()
                .likeIfPresent(Disposal::getCode, pageVO.getCode())
                .likeIfPresent(Disposal::getName, pageVO.getName())
                .orderByAsc(Disposal::getGrade));
    }

    default Disposal selectByCode(String code) {
        return selectOne(Disposal::getCode, code);
    }

    default Disposal selectByName(String name) {
        return selectOne(Disposal::getName, name);
    }
}
