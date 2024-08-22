package cn.wnhyang.coolGuard.system.mapper;


import cn.wnhyang.coolGuard.mybatis.BaseMapperX;
import cn.wnhyang.coolGuard.mybatis.LambdaQueryWrapperX;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.system.entity.DictDataPO;
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
public interface DictDataMapper extends BaseMapperX<DictDataPO> {

    default PageResult<DictDataPO> selectPage(DictDataPageVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DictDataPO>()
                .likeIfPresent(DictDataPO::getLabel, reqVO.getLabel())
                .eqIfPresent(DictDataPO::getDictType, reqVO.getDictType())
                .eqIfPresent(DictDataPO::getStatus, reqVO.getStatus())
                .orderByDesc(Arrays.asList(DictDataPO::getDictType, DictDataPO::getSort)));
    }

    default long selectCountByDictType(String dictType) {
        return selectCount(DictDataPO::getDictType, dictType);
    }

    default DictDataPO selectByDictTypeAndValue(String dictType, String value) {
        return selectOne(DictDataPO::getDictType, dictType, DictDataPO::getValue, value);
    }

    default List<DictDataPO> selectListByDictType(String type) {
        return selectList(DictDataPO::getDictType, type);
    }
}
