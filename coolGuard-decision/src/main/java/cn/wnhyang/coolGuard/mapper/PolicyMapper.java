package cn.wnhyang.coolGuard.mapper;

import cn.wnhyang.coolGuard.constant.RedisKey;
import cn.wnhyang.coolGuard.entity.Policy;
import cn.wnhyang.coolGuard.mybatis.BaseMapperX;
import cn.wnhyang.coolGuard.mybatis.LambdaQueryWrapperX;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.page.PolicyPageVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * 策略表 Mapper 接口
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@Mapper
public interface PolicyMapper extends BaseMapperX<Policy> {

    default PageResult<Policy> selectPage(PolicyPageVO pageVO) {
        return selectPage(pageVO, new LambdaQueryWrapperX<Policy>());
    }

    @Cacheable(value = RedisKey.POLICY + "::sCode", key = "#setCode", unless = "#result == null")
    default List<Policy> selectListBySetCode(String setCode) {
        return selectList(Policy::getPolicySetCode, setCode);
    }

    @Cacheable(value = RedisKey.POLICY + "::co", key = "#code", unless = "#result == null")
    default Policy selectByCode(String code) {
        return selectOne(Policy::getCode, code);
    }

    default List<Policy> selectList(List<String> codes, String name, String code) {
        return selectList(new LambdaQueryWrapperX<Policy>()
                .inIfPresent(Policy::getCode, codes)
                .likeIfPresent(Policy::getName, name)
                .eqIfPresent(Policy::getCode, code));
    }

}
