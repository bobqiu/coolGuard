package cn.wnhyang.coolguard.system.mapper;


import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.mybatis.mapper.BaseMapperX;
import cn.wnhyang.coolguard.mybatis.wrapper.LambdaQueryWrapperX;
import cn.wnhyang.coolguard.system.entity.DictDataDO;
import cn.wnhyang.coolguard.system.vo.dictdata.DictDataPageVO;
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
