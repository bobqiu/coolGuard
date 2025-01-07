package cn.wnhyang.coolGuard.system.mapper;

import cn.wnhyang.coolGuard.mybatis.BaseMapperX;
import cn.wnhyang.coolGuard.mybatis.LambdaQueryWrapperX;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.system.entity.OperateLog;
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
public interface OperateLogMapper extends BaseMapperX<OperateLog> {

    default PageResult<OperateLog> selectPage(OperateLogPageVO reqVO, Collection<Long> userIds) {
        LambdaQueryWrapperX<OperateLog> query = new LambdaQueryWrapperX<OperateLog>()
                .likeIfPresent(OperateLog::getModule, reqVO.getModule())
                .inIfPresent(OperateLog::getUserId, userIds)
                .eqIfPresent(OperateLog::getType, reqVO.getType())
                .betweenIfPresent(OperateLog::getStartTime, reqVO.getStartTime(), reqVO.getEndTime())
                .eqIfPresent(OperateLog::getResultCode, reqVO.getResultCode());
        // 降序
        query.orderByDesc(OperateLog::getId);
        return selectPage(reqVO, query);
    }
}
