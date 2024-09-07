package cn.wnhyang.coolGuard.mapper;

import cn.wnhyang.coolGuard.entity.PolicySetVersionExt;
import cn.wnhyang.coolGuard.mybatis.BaseMapperX;
import cn.wnhyang.coolGuard.mybatis.LambdaQueryWrapperX;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.page.PolicySetVersionExtPageVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 策略集版本扩展表 Mapper 接口
 *
 * @author wnhyang
 * @since 2024/08/29
 */
@Mapper
public interface PolicySetVersionExtMapper extends BaseMapperX<PolicySetVersionExt> {

    default PageResult<PolicySetVersionExt> selectPage(PolicySetVersionExtPageVO pageVO) {
        return selectPage(pageVO, new LambdaQueryWrapperX<PolicySetVersionExt>());
    }
}
