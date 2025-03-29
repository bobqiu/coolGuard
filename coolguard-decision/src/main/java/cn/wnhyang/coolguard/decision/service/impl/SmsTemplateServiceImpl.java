package cn.wnhyang.coolguard.decision.service.impl;

import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import cn.wnhyang.coolguard.common.entity.LabelValue;
import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.common.util.CollectionUtils;
import cn.wnhyang.coolguard.decision.context.DecisionContextHolder;
import cn.wnhyang.coolguard.decision.context.FieldContext;
import cn.wnhyang.coolguard.decision.convert.SmsTemplateConvert;
import cn.wnhyang.coolguard.decision.entity.Action;
import cn.wnhyang.coolguard.decision.entity.SmsTemplate;
import cn.wnhyang.coolguard.decision.mapper.SmsTemplateMapper;
import cn.wnhyang.coolguard.decision.service.SmsTemplateService;
import cn.wnhyang.coolguard.decision.vo.create.SmsTemplateCreateVO;
import cn.wnhyang.coolguard.decision.vo.page.SmsTemplatePageVO;
import cn.wnhyang.coolguard.decision.vo.update.SmsTemplateUpdateVO;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.regex.Pattern;

import static cn.wnhyang.coolguard.common.exception.util.ServiceExceptionUtil.exception;
import static cn.wnhyang.coolguard.decision.error.DecisionErrorCode.SMS_TEMPLATE_CODE_EXIST;
import static cn.wnhyang.coolguard.decision.error.DecisionErrorCode.SMS_TEMPLATE_NOT_EXIST;

/**
 * 消息模版表 服务实现类
 *
 * @author wnhyang
 * @since 2024/12/08
 */
@Slf4j
@Service
@LiteflowComponent
@RequiredArgsConstructor
public class SmsTemplateServiceImpl implements SmsTemplateService {

    /**
     * 正则表达式，匹配 {} 中的变量
     */
    private static final Pattern PATTERN_PARAMS = Pattern.compile("\\{(.*?)}");

    private final SmsTemplateMapper smsTemplateMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long create(SmsTemplateCreateVO createVO) {
        if (smsTemplateMapper.selectByCode(createVO.getCode()) != null) {
            throw exception(SMS_TEMPLATE_CODE_EXIST);
        }
        SmsTemplate smsTemplate = SmsTemplateConvert.INSTANCE.convert(createVO);
        smsTemplate.setParams(parseTemplateContentParams(smsTemplate.getContent()));
        smsTemplateMapper.insert(smsTemplate);
        return smsTemplate.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SmsTemplateUpdateVO updateVO) {
        SmsTemplate smsTemplate = smsTemplateMapper.selectById(updateVO.getId());
        if (smsTemplate == null) {
            throw exception(SMS_TEMPLATE_NOT_EXIST);
        }
        SmsTemplate convert = SmsTemplateConvert.INSTANCE.convert(updateVO);
        convert.setParams(parseTemplateContentParams(convert.getContent()));
        smsTemplateMapper.updateById(convert);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        SmsTemplate smsTemplate = smsTemplateMapper.selectById(id);
        if (smsTemplate == null) {
            throw exception(SMS_TEMPLATE_NOT_EXIST);
        }
        // TODO 引用
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

    @Override
    public List<LabelValue> getLabelValueList() {
        return CollectionUtils.convertList(smsTemplateMapper.selectList(), SmsTemplate::getLabelValue);
    }

    @Override
    public void sendSms(Action.SendSms sendSms) {
        if (sendSms == null) {
            return;
        }
        FieldContext fieldContext = DecisionContextHolder.getFieldContext();
        // TODO 完善
        SmsTemplate smsTemplate = smsTemplateMapper.selectByCode(sendSms.getSmsTemplateCode());
        if (smsTemplate == null) {
            return;
        }
        String smsContent = StrUtil.format(smsTemplate.getContent(), fieldContext);
        log.info("smsContent: {}", smsContent);
    }

    List<String> parseTemplateContentParams(String content) {
        return ReUtil.findAllGroup1(PATTERN_PARAMS, content);
    }
}
