package cn.wnhyang.coolGuard.system.mapper;

import cn.wnhyang.coolGuard.common.pojo.PageResult;
import cn.wnhyang.coolGuard.mybatis.mapper.BaseMapperX;
import cn.wnhyang.coolGuard.mybatis.wrapper.LambdaQueryWrapperX;
import cn.wnhyang.coolGuard.system.entity.OperateLogDO;
import cn.wnhyang.coolGuard.system.vo.operatelog.OperateLogPageVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 操作日志记录
 *
 * @author wnhyang
 * @since 2023/06/05
 */
@Mapper
public interface OperateLogMapper extends BaseMapperX<OperateLogDO> {

    default PageResult<OperateLogDO> selectPage(OperateLogPageVO reqVO) {
        LambdaQueryWrapperX<OperateLogDO> query = new LambdaQueryWrapperX<OperateLogDO>()
                .likeIfPresent(OperateLogDO::getModule, reqVO.getModule())
                .likeIfPresent(OperateLogDO::getUserNickname, reqVO.getUserNickname())
                .eqIfPresent(OperateLogDO::getType, reqVO.getType())
                .betweenIfPresent(OperateLogDO::getStartTime, reqVO.getStartTime(), reqVO.getEndTime())
                .eqIfPresent(OperateLogDO::getResultCode, reqVO.getResultCode());
        // 降序
        query.orderByDesc(OperateLogDO::getId);
        return selectPage(reqVO, query);
    }
}
