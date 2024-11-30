package cn.wnhyang.coolGuard.mapper;

import cn.wnhyang.coolGuard.entity.PolicyVersion;
import cn.wnhyang.coolGuard.mybatis.BaseMapperX;
import cn.wnhyang.coolGuard.mybatis.LambdaQueryWrapperX;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.page.PolicyVersionPageVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 策略版本表 Mapper 接口
 *
 * @author wnhyang
 * @since 2024/08/29
 */
@Mapper
public interface PolicyVersionMapper extends BaseMapperX<PolicyVersion> {

    default PageResult<PolicyVersion> selectPage(PolicyVersionPageVO pageVO) {
        return selectPage(pageVO, new LambdaQueryWrapperX<PolicyVersion>());
    }
}
