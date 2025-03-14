package cn.wnhyang.coolguard.system.mapper;

import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.mybatis.mapper.BaseMapperX;
import cn.wnhyang.coolguard.mybatis.wrapper.LambdaQueryWrapperX;
import cn.wnhyang.coolguard.system.entity.OperateLogDO;
import cn.wnhyang.coolguard.system.vo.operatelog.OperateLogPageVO;
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
