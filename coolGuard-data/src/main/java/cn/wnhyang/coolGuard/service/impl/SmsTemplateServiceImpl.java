package cn.wnhyang.coolGuard.service.impl;

import cn.wnhyang.coolGuard.convert.SmsTemplateConvert;
import cn.wnhyang.coolGuard.entity.SmsTemplate;
import cn.wnhyang.coolGuard.mapper.SmsTemplateMapper;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.SmsTemplateService;
import cn.wnhyang.coolGuard.vo.create.SmsTemplateCreateVO;
import cn.wnhyang.coolGuard.vo.page.SmsTemplatePageVO;
import cn.wnhyang.coolGuard.vo.update.SmsTemplateUpdateVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 消息模版表 服务实现类
 *
 * @author wnhyang
 * @since 2024/12/08
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SmsTemplateServiceImpl implements SmsTemplateService {

    private final SmsTemplateMapper smsTemplateMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long create(SmsTemplateCreateVO createVO) {
        SmsTemplate smsTemplate = SmsTemplateConvert.INSTANCE.convert(createVO);
        smsTemplateMapper.insert(smsTemplate);
        return smsTemplate.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SmsTemplateUpdateVO updateVO) {
        SmsTemplate smsTemplate = SmsTemplateConvert.INSTANCE.convert(updateVO);
        smsTemplateMapper.updateById(smsTemplate);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        smsTemplateMapper.deleteById(id);
    }

    @Override
    public SmsTemplate get(Long id) {
        return smsTemplateMapper.selectById(id);
    }

    @Override
    public PageResult<SmsTemplate> page(SmsTemplatePageVO pageVO) {
        return smsTemplateMapper.selectPage(pageVO);
    }

}
