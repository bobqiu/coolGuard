package cn.wnhyang.coolguard.decision.convert;

import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.decision.entity.SmsTemplate;
import cn.wnhyang.coolguard.decision.vo.SmsTemplateVO;
import cn.wnhyang.coolguard.decision.vo.create.SmsTemplateCreateVO;
import cn.wnhyang.coolguard.decision.vo.update.SmsTemplateUpdateVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 消息模版表
 *
 * @author wnhyang
 * @since 2024/12/08
 */
@Mapper
public interface SmsTemplateConvert {

    SmsTemplateConvert INSTANCE = Mappers.getMapper(SmsTemplateConvert.class);

    SmsTemplate convert(SmsTemplateCreateVO createVO);

    SmsTemplate convert(SmsTemplateUpdateVO updateVO);

    SmsTemplateVO convert(SmsTemplate po);

    PageResult<SmsTemplateVO> convert(PageResult<SmsTemplate> pageResult);

}
