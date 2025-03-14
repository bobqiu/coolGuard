package cn.wnhyang.coolguard.system.mapper;

import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.mybatis.mapper.BaseMapperX;
import cn.wnhyang.coolguard.mybatis.wrapper.LambdaQueryWrapperX;
import cn.wnhyang.coolguard.system.entity.LoginLogDO;
import cn.wnhyang.coolguard.system.vo.loginlog.LoginLogPageVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统访问记录
 *
 * @author wnhyang
 * @since 2023/07/25
 */
@Mapper
public interface LoginLogMapper extends BaseMapperX<LoginLogDO> {

    default PageResult<LoginLogDO> selectPage(LoginLogPageVO reqVO) {
        LambdaQueryWrapperX<LoginLogDO> query = new LambdaQueryWrapperX<LoginLogDO>()
                .eqIfPresent(LoginLogDO::getLoginType, reqVO.getLoginType())
                .likeIfPresent(LoginLogDO::getUserIp, reqVO.getUserIp())
                .likeIfPresent(LoginLogDO::getAccount, reqVO.getAccount())
                .betweenIfPresent(LoginLogDO::getCreateTime, reqVO.getStartTime(), reqVO.getEndTime())
                .eqIfPresent(LoginLogDO::getResult, reqVO.getResult());
        // 降序
        query.orderByDesc(LoginLogDO::getId);
        return selectPage(reqVO, query);
    }
}
