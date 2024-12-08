package cn.wnhyang.coolGuard.mapper;

import cn.wnhyang.coolGuard.entity.SmsTemplate;
import cn.wnhyang.coolGuard.mybatis.BaseMapperX;
import cn.wnhyang.coolGuard.mybatis.LambdaQueryWrapperX;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.page.SmsTemplatePageVO;
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
        return selectPage(pageVO, new LambdaQueryWrapperX<SmsTemplate>());
    }
}
