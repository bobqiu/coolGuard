package cn.wnhyang.coolGuard.system.mapper;

import cn.wnhyang.coolGuard.common.pojo.PageResult;
import cn.wnhyang.coolGuard.mybatis.mapper.BaseMapperX;
import cn.wnhyang.coolGuard.mybatis.wrapper.LambdaQueryWrapperX;
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
        return selectPage(pageVO, new LambdaQueryWrapperX<ParamDO>()
                .likeIfPresent(ParamDO::getName, pageVO.getName())
                .likeIfPresent(ParamDO::getCode, pageVO.getCode())
                .eqIfPresent(ParamDO::getType, pageVO.getType())
                .likeIfPresent(ParamDO::getData, pageVO.getData())
        );
    }

    default ParamDO selectByName(String name) {
        return selectOne(ParamDO::getName, name);
    }

    default ParamDO selectByCode(String code) {
        return selectOne(ParamDO::getCode, code);
    }
}
