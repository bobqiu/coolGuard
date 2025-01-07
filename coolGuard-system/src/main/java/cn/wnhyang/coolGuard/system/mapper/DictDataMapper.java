package cn.wnhyang.coolGuard.system.mapper;


import cn.wnhyang.coolGuard.mybatis.BaseMapperX;
import cn.wnhyang.coolGuard.mybatis.LambdaQueryWrapperX;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.system.entity.DictDataDO;
import cn.wnhyang.coolGuard.system.vo.dictdata.DictDataPageVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Arrays;
import java.util.List;

/**
 * 字典数据表 Mapper 接口
 *
 * @author wnhyang
 * @since 2023/09/13
 */
@Mapper
public interface DictDataMapper extends BaseMapperX<DictDataDO> {

    default PageResult<DictDataDO> selectPage(DictDataPageVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DictDataDO>()
                .likeIfPresent(DictDataDO::getLabel, reqVO.getLabel())
                .eqIfPresent(DictDataDO::getDictType, reqVO.getDictType())
                .eqIfPresent(DictDataDO::getStatus, reqVO.getStatus())
                .orderByDesc(Arrays.asList(DictDataDO::getDictType, DictDataDO::getSort)));
    }

    default long selectCountByDictType(String dictType) {
        return selectCount(DictDataDO::getDictType, dictType);
    }

    default DictDataDO selectByDictTypeAndValue(String dictType, String value) {
        return selectOne(DictDataDO::getDictType, dictType, DictDataDO::getValue, value);
    }

    default List<DictDataDO> selectListByDictType(String type) {
        return selectList(DictDataDO::getDictType, type);
    }
}
