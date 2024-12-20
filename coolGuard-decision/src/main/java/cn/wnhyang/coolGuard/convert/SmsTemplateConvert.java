package cn.wnhyang.coolGuard.convert;

import cn.wnhyang.coolGuard.entity.SmsTemplate;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.SmsTemplateVO;
import cn.wnhyang.coolGuard.vo.create.SmsTemplateCreateVO;
import cn.wnhyang.coolGuard.vo.update.SmsTemplateUpdateVO;
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
