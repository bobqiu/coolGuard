package cn.wnhyang.coolGuard.mapper;

import cn.wnhyang.coolGuard.entity.Chain;
import cn.wnhyang.coolGuard.mybatis.BaseMapperX;
import cn.wnhyang.coolGuard.mybatis.LambdaQueryWrapperX;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.page.ChainPageVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * chain表 Mapper 接口
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@Mapper
public interface ChainMapper extends BaseMapperX<Chain> {

    default PageResult<Chain> selectPage(ChainPageVO pageVO) {
        return selectPage(pageVO, new LambdaQueryWrapperX<Chain>());
    }
}
