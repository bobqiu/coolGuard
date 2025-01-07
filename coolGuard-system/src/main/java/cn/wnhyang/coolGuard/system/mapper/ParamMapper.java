package cn.wnhyang.coolGuard.system.mapper;

import cn.wnhyang.coolGuard.mybatis.BaseMapperX;
import cn.wnhyang.coolGuard.mybatis.LambdaQueryWrapperX;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.system.entity.Param;
import cn.wnhyang.coolGuard.system.vo.param.ParamPageVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 参数表 Mapper 接口
 *
 * @author wnhyang
 * @since 2025/01/07
 */
@Mapper
public interface ParamMapper extends BaseMapperX<Param> {

    default PageResult<Param> selectPage(ParamPageVO pageVO) {
        return selectPage(pageVO, new LambdaQueryWrapperX<Param>());
    }

    default Param selectByLabel(String label) {
        return selectOne(new LambdaQueryWrapperX<Param>().eq(Param::getLabel, label));
    }

    default Param selectByValue(String value) {
        return selectOne(new LambdaQueryWrapperX<Param>().eq(Param::getValue, value));
    }
}
