package cn.wnhyang.coolGuard.system.mapper;

import cn.wnhyang.coolGuard.mybatis.BaseMapperX;
import cn.wnhyang.coolGuard.mybatis.LambdaQueryWrapperX;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.system.entity.Dict;
import cn.wnhyang.coolGuard.system.vo.dict.DictPageVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 字典表 Mapper 接口
 *
 * @author wnhyang
 * @since 2025/01/03
 */
@Mapper
public interface DictMapper extends BaseMapperX<Dict> {

    default PageResult<Dict> selectPage(DictPageVO pageVO) {
        return selectPage(pageVO, new LambdaQueryWrapperX<Dict>()
                .likeIfPresent(Dict::getLabel, pageVO.getLabel())
                .likeIfPresent(Dict::getValue, pageVO.getValue())
        );
    }

    default Dict selectByLabel(String label) {
        return selectOne(new LambdaQueryWrapperX<Dict>().eq(Dict::getLabel, label));
    }

    default Dict selectByValue(String value) {
        return selectOne(new LambdaQueryWrapperX<Dict>().eq(Dict::getValue, value));
    }
}
