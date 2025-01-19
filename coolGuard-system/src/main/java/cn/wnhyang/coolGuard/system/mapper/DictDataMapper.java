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
                .likeIfPresent(DictDataDO::getName, reqVO.getName())
                .eqIfPresent(DictDataDO::getTypeCode, reqVO.getTypeCode())
                .eqIfPresent(DictDataDO::getStatus, reqVO.getStatus())
                .orderByDesc(Arrays.asList(DictDataDO::getTypeCode, DictDataDO::getSort)));
    }


    default DictDataDO selectByTypeCodeAndCode(String typeCode, String code) {
        return selectOne(DictDataDO::getTypeCode, typeCode, DictDataDO::getCode, code);
    }

    default List<DictDataDO> selectListByDictTypeCode(String typeCode) {
        return selectList(new LambdaQueryWrapperX<DictDataDO>()
                .eqIfPresent(DictDataDO::getTypeCode, typeCode));
    }

    default int deleteByTypeCode(String typeCode) {
        return delete(DictDataDO::getTypeCode, typeCode);
    }
}
