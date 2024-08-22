package cn.wnhyang.coolGuard.system.mapper;

import cn.wnhyang.coolGuard.mybatis.BaseMapperX;
import cn.wnhyang.coolGuard.mybatis.LambdaQueryWrapperX;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.system.entity.OperateLogPO;
import cn.wnhyang.coolGuard.system.vo.operatelog.OperateLogPageVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;

/**
 * 操作日志记录
 *
 * @author wnhyang
 * @since 2023/06/05
 */
@Mapper
public interface OperateLogMapper extends BaseMapperX<OperateLogPO> {

    default PageResult<OperateLogPO> selectPage(OperateLogPageVO reqVO, Collection<Long> userIds) {
        LambdaQueryWrapperX<OperateLogPO> query = new LambdaQueryWrapperX<OperateLogPO>()
                .likeIfPresent(OperateLogPO::getModule, reqVO.getModule())
                .inIfPresent(OperateLogPO::getUserId, userIds)
                .eqIfPresent(OperateLogPO::getType, reqVO.getType())
                .betweenIfPresent(OperateLogPO::getStartTime, reqVO.getStartTime(), reqVO.getEndTime())
                .eqIfPresent(OperateLogPO::getResultCode, reqVO.getResultCode());
        // 降序
        query.orderByDesc(OperateLogPO::getId);
        return selectPage(reqVO, query);
    }
}
