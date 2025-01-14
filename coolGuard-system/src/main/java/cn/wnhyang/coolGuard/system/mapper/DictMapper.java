package cn.wnhyang.coolGuard.system.mapper;

import cn.wnhyang.coolGuard.mybatis.BaseMapperX;
import cn.wnhyang.coolGuard.mybatis.LambdaQueryWrapperX;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.system.entity.DictDO;
import cn.wnhyang.coolGuard.system.vo.dict.DictPageVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 字典表 Mapper 接口
 *
 * @author wnhyang
 * @since 2025/01/03
 */
@Mapper
public interface DictMapper extends BaseMapperX<DictDO> {

    default PageResult<DictDO> selectPage(DictPageVO pageVO) {
        return selectPage(pageVO, new LambdaQueryWrapperX<DictDO>()
                .likeIfPresent(DictDO::getLabel, pageVO.getLabel())
                .likeIfPresent(DictDO::getValue, pageVO.getValue())
        );
    }

    default DictDO selectByLabel(String label) {
        return selectOne(new LambdaQueryWrapperX<DictDO>().eq(DictDO::getLabel, label));
    }

    default DictDO selectByValue(String value) {
        return selectOne(new LambdaQueryWrapperX<DictDO>().eq(DictDO::getValue, value));
    }
}
