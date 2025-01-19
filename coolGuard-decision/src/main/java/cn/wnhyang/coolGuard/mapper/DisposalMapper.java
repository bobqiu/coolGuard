package cn.wnhyang.coolGuard.mapper;

import cn.wnhyang.coolGuard.constant.RedisKey;
import cn.wnhyang.coolGuard.entity.Disposal;
import cn.wnhyang.coolGuard.mybatis.BaseMapperX;
import cn.wnhyang.coolGuard.mybatis.LambdaQueryWrapperX;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.page.DisposalPageVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.cache.annotation.Cacheable;

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
                .likeIfPresent(Disposal::getName, pageVO.getName()));
    }

    @Cacheable(value = RedisKey.DISPOSAL + "::co", key = "#code", unless = "#result == null")
    default Disposal selectByCode(String code) {
        return selectOne(Disposal::getCode, code);
    }
}
