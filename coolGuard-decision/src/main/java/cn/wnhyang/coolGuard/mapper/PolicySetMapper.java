package cn.wnhyang.coolGuard.mapper;

import cn.wnhyang.coolGuard.constant.RedisKey;
import cn.wnhyang.coolGuard.entity.PolicySet;
import cn.wnhyang.coolGuard.mybatis.BaseMapperX;
import cn.wnhyang.coolGuard.mybatis.LambdaQueryWrapperX;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.page.PolicySetPageVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Set;

/**
 * 策略集表 Mapper 接口
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@Mapper
public interface PolicySetMapper extends BaseMapperX<PolicySet> {

    default PageResult<PolicySet> selectPage(PolicySetPageVO pageVO) {
        return selectPage(pageVO, new LambdaQueryWrapperX<PolicySet>());
    }

    @Cacheable(cacheNames = RedisKey.POLICY_SET + "::co", key = "#{code}", unless = "#result == null")
    default PolicySet selectByCode(String code) {
        return selectOne(PolicySet::getCode, code);
    }

    @Cacheable(cacheNames = RedisKey.POLICY_SET + "::a-c", key = "#appName+'-'+#code", unless = "#result == null")
    default PolicySet selectByAppNameAndCode(String appName, String code) {
        return selectOne(PolicySet::getAppName, appName, PolicySet::getCode, code);
    }

    default List<PolicySet> selectList(Set<String> setCodes, String appName, String name, String code) {
        return selectList(new LambdaQueryWrapperX<PolicySet>()
                .inIfPresent(PolicySet::getCode, setCodes)
                .eqIfPresent(PolicySet::getAppName, appName)
                .likeIfPresent(PolicySet::getName, name)
                .eqIfPresent(PolicySet::getCode, code));
    }
}
