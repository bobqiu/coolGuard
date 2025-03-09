package cn.wnhyang.coolGuard.decision.mapper;

import cn.wnhyang.coolGuard.common.pojo.PageResult;
import cn.wnhyang.coolGuard.decision.entity.SmsTemplate;
import cn.wnhyang.coolGuard.decision.vo.page.SmsTemplatePageVO;
import cn.wnhyang.coolGuard.mybatis.mapper.BaseMapperX;
import cn.wnhyang.coolGuard.mybatis.wrapper.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

/**
 * 消息模版表 Mapper 接口
 *
 * @author wnhyang
 * @since 2024/12/08
 */
@Mapper
public interface SmsTemplateMapper extends BaseMapperX<SmsTemplate> {

    default PageResult<SmsTemplate> selectPage(SmsTemplatePageVO pageVO) {
        return selectPage(pageVO, new LambdaQueryWrapperX<SmsTemplate>()
                .likeIfPresent(SmsTemplate::getName, pageVO.getName())
                .likeIfPresent(SmsTemplate::getCode, pageVO.getCode()));
    }

    default SmsTemplate selectByCode(String code) {
        return selectOne(SmsTemplate::getCode, code);
    }
}
