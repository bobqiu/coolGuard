package cn.wnhyang.coolGuard.mapper;

import cn.wnhyang.coolGuard.entity.PolicyVersionExt;
import cn.wnhyang.coolGuard.mybatis.BaseMapperX;
import cn.wnhyang.coolGuard.mybatis.LambdaQueryWrapperX;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.page.PolicyVersionExtPageVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 策略版本扩展表 Mapper 接口
 *
 * @author wnhyang
 * @since 2024/08/29
 */
@Mapper
public interface PolicyVersionExtMapper extends BaseMapperX<PolicyVersionExt> {

    default PageResult<PolicyVersionExt> selectPage(PolicyVersionExtPageVO pageVO) {
        return selectPage(pageVO, new LambdaQueryWrapperX<PolicyVersionExt>());
    }
}
