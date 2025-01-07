package cn.wnhyang.coolGuard.system.mapper;

import cn.wnhyang.coolGuard.mybatis.BaseMapperX;
import cn.wnhyang.coolGuard.mybatis.LambdaQueryWrapperX;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.system.entity.LoginLog;
import cn.wnhyang.coolGuard.system.vo.loginlog.LoginLogPageVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统访问记录
 *
 * @author wnhyang
 * @since 2023/07/25
 */
@Mapper
public interface LoginLogMapper extends BaseMapperX<LoginLog> {

    default PageResult<LoginLog> selectPage(LoginLogPageVO reqVO) {
        LambdaQueryWrapperX<LoginLog> query = new LambdaQueryWrapperX<LoginLog>()
                .eqIfPresent(LoginLog::getLoginType, reqVO.getLoginType())
                .likeIfPresent(LoginLog::getUserIp, reqVO.getUserIp())
                .likeIfPresent(LoginLog::getAccount, reqVO.getAccount())
                .betweenIfPresent(LoginLog::getCreateTime, reqVO.getStartTime(), reqVO.getEndTime())
                .eqIfPresent(LoginLog::getResult, reqVO.getResult());
        // 降序
        query.orderByDesc(LoginLog::getId);
        return selectPage(reqVO, query);
    }
}
