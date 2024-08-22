package cn.wnhyang.coolGuard.system.mapper;

import cn.wnhyang.coolGuard.mybatis.BaseMapperX;
import cn.wnhyang.coolGuard.mybatis.LambdaQueryWrapperX;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.system.entity.LoginLogPO;
import cn.wnhyang.coolGuard.system.vo.loginlog.LoginLogPageVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统访问记录
 *
 * @author wnhyang
 * @since 2023/07/25
 */
@Mapper
public interface LoginLogMapper extends BaseMapperX<LoginLogPO> {

    default PageResult<LoginLogPO> selectPage(LoginLogPageVO reqVO) {
        LambdaQueryWrapperX<LoginLogPO> query = new LambdaQueryWrapperX<LoginLogPO>()
                .eqIfPresent(LoginLogPO::getLoginType, reqVO.getLoginType())
                .likeIfPresent(LoginLogPO::getUserIp, reqVO.getUserIp())
                .likeIfPresent(LoginLogPO::getAccount, reqVO.getAccount())
                .betweenIfPresent(LoginLogPO::getCreateTime, reqVO.getStartTime(), reqVO.getEndTime())
                .eqIfPresent(LoginLogPO::getResult, reqVO.getResult());
        // 降序
        query.orderByDesc(LoginLogPO::getId);
        return selectPage(reqVO, query);
    }
}
