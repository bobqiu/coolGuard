package cn.wnhyang.coolguard.decision.mapper;

import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.decision.entity.Access;
import cn.wnhyang.coolguard.decision.vo.page.AccessPageVO;
import cn.wnhyang.coolguard.mybatis.mapper.BaseMapperX;
import cn.wnhyang.coolguard.mybatis.wrapper.LambdaQueryWrapperX;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.apache.ibatis.annotations.Mapper;

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
                .likeIfPresent(Access::getCode, pageVO.getCode())
                .likeIfPresent(Access::getName, pageVO.getName()));
    }

    default Access selectByCode(String code) {
        return selectOne(Access::getCode, code);
    }

    default void updateByCode(Access access) {
        update(access, new LambdaUpdateWrapper<Access>()
                .eq(Access::getCode, access.getCode()));
    }
}
