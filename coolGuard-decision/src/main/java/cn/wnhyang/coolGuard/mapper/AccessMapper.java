package cn.wnhyang.coolGuard.mapper;

import cn.wnhyang.coolGuard.entity.Access;
import cn.wnhyang.coolGuard.mybatis.BaseMapperX;
import cn.wnhyang.coolGuard.mybatis.LambdaQueryWrapperX;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.page.AccessPageVO;
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
