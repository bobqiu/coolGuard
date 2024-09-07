package cn.wnhyang.coolGuard.mapper;

import cn.wnhyang.coolGuard.entity.PolicySetVersion;
import cn.wnhyang.coolGuard.mybatis.BaseMapperX;
import cn.wnhyang.coolGuard.mybatis.LambdaQueryWrapperX;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.page.PolicySetVersionPageVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 策略集版本表 Mapper 接口
 *
 * @author wnhyang
 * @since 2024/08/29
 */
@Mapper
public interface PolicySetVersionMapper extends BaseMapperX<PolicySetVersion> {

    default PageResult<PolicySetVersion> selectPage(PolicySetVersionPageVO pageVO) {
        return selectPage(pageVO, new LambdaQueryWrapperX<PolicySetVersion>());
    }
}
