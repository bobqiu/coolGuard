package cn.wnhyang.coolGuard.system.mapper;

import cn.wnhyang.coolGuard.mybatis.BaseMapperX;
import cn.wnhyang.coolGuard.mybatis.LambdaQueryWrapperX;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.system.entity.ParamDO;
import cn.wnhyang.coolGuard.system.vo.param.ParamPageVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 参数表 Mapper 接口
 *
 * @author wnhyang
 * @since 2025/01/07
 */
@Mapper
public interface ParamMapper extends BaseMapperX<ParamDO> {

    default PageResult<ParamDO> selectPage(ParamPageVO pageVO) {
        return selectPage(pageVO, new LambdaQueryWrapperX<ParamDO>());
    }

    default ParamDO selectByLabel(String label) {
        return selectOne(new LambdaQueryWrapperX<ParamDO>().eq(ParamDO::getLabel, label));
    }

    default ParamDO selectByValue(String value) {
        return selectOne(new LambdaQueryWrapperX<ParamDO>().eq(ParamDO::getValue, value));
    }
}
