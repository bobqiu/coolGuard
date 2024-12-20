package cn.wnhyang.coolGuard.mapper;

import cn.wnhyang.coolGuard.constant.RedisKey;
import cn.wnhyang.coolGuard.entity.Access;
import cn.wnhyang.coolGuard.mybatis.BaseMapperX;
import cn.wnhyang.coolGuard.mybatis.LambdaQueryWrapperX;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.page.AccessPageVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.cache.annotation.Cacheable;

/**
 * 接入表 Mapper 接口
 *
 * @author wnhyang
 * @since 2024/03/14
 */
@Mapper
public interface AccessMapper extends BaseMapperX<Access> {

    default PageResult<Access> selectPage(AccessPageVO pageVO) {
        return selectPage(pageVO, new LambdaQueryWrapperX<Access>()
                .likeIfPresent(Access::getName, pageVO.getName())
                .likeIfPresent(Access::getDisplayName, pageVO.getDisplayName()));
    }

    @Cacheable(cacheNames = RedisKey.ACCESS + "::na", key = "#name", unless = "#result == null")
    default Access selectByName(String name) {
        return selectOne(Access::getName, name);
    }

}
