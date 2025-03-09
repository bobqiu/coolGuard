package cn.wnhyang.coolGuard.system.mapper;


import cn.wnhyang.coolGuard.common.pojo.PageResult;
import cn.wnhyang.coolGuard.mybatis.mapper.BaseMapperX;
import cn.wnhyang.coolGuard.mybatis.wrapper.LambdaQueryWrapperX;
import cn.wnhyang.coolGuard.system.entity.DictTypeDO;
import cn.wnhyang.coolGuard.system.vo.dicttype.DictTypePageVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 字典类型表 Mapper 接口
 *
 * @author wnhyang
 * @since 2023/09/13
 */
@Mapper
public interface DictTypeMapper extends BaseMapperX<DictTypeDO> {

    default PageResult<DictTypeDO> selectPage(DictTypePageVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DictTypeDO>()
                .likeIfPresent(DictTypeDO::getName, reqVO.getName())
                .likeIfPresent(DictTypeDO::getCode, reqVO.getCode())
                .orderByDesc(DictTypeDO::getId));
    }

    default DictTypeDO selectByName(String name) {
        return selectOne(DictTypeDO::getName, name);
    }

    default DictTypeDO selectByCode(String code) {
        return selectOne(DictTypeDO::getCode, code);
    }
}
