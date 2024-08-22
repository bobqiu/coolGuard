package cn.wnhyang.coolGuard.system.mapper;


import cn.wnhyang.coolGuard.mybatis.BaseMapperX;
import cn.wnhyang.coolGuard.mybatis.LambdaQueryWrapperX;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.system.entity.DictTypePO;
import cn.wnhyang.coolGuard.system.vo.dicttype.DictTypePageVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 字典类型表 Mapper 接口
 *
 * @author wnhyang
 * @since 2023/09/13
 */
@Mapper
public interface DictTypeMapper extends BaseMapperX<DictTypePO> {

    default DictTypePO selectByType(String type) {
        return selectOne(DictTypePO::getType, type);
    }

    default DictTypePO selectByName(String name) {
        return selectOne(DictTypePO::getName, name);
    }

    default PageResult<DictTypePO> selectPage(DictTypePageVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DictTypePO>()
                .likeIfPresent(DictTypePO::getName, reqVO.getName())
                .likeIfPresent(DictTypePO::getType, reqVO.getType())
                .eqIfPresent(DictTypePO::getStatus, reqVO.getStatus())
                .betweenIfPresent(DictTypePO::getCreateTime, reqVO.getStartTime(), reqVO.getEndTime())
                .orderByDesc(DictTypePO::getId));
    }
}
