package cn.wnhyang.coolGuard.service.impl;

import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import cn.wnhyang.coolGuard.context.FieldContext;
import cn.wnhyang.coolGuard.convert.SmsTemplateConvert;
import cn.wnhyang.coolGuard.entity.Action;
import cn.wnhyang.coolGuard.entity.LabelValue;
import cn.wnhyang.coolGuard.entity.SmsTemplate;
import cn.wnhyang.coolGuard.mapper.SmsTemplateMapper;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.SmsTemplateService;
import cn.wnhyang.coolGuard.util.CollectionUtils;
import cn.wnhyang.coolGuard.util.LFUtil;
import cn.wnhyang.coolGuard.vo.create.SmsTemplateCreateVO;
import cn.wnhyang.coolGuard.vo.page.SmsTemplatePageVO;
import cn.wnhyang.coolGuard.vo.update.SmsTemplateUpdateVO;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.annotation.LiteflowMethod;
import com.yomahub.liteflow.core.NodeComponent;
import com.yomahub.liteflow.enums.LiteFlowMethodEnum;
import com.yomahub.liteflow.enums.NodeTypeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.regex.Pattern;

import static cn.wnhyang.coolGuard.error.DecisionErrorCode.SMS_TEMPLATE_CODE_EXIST;
import static cn.wnhyang.coolGuard.error.DecisionErrorCode.SMS_TEMPLATE_NOT_EXIST;
import static cn.wnhyang.coolGuard.exception.util.ServiceExceptionUtil.exception;

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

    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS, nodeId = LFUtil.SEND_SMS, nodeType = NodeTypeEnum.COMMON, nodeName = "加入名单组件")
    public void sendSms(NodeComponent bindCmp) {
        // TODO 完善
        Action.SendSms sendSms = bindCmp.getCmpData(Action.SendSms.class);
        SmsTemplate smsTemplate = smsTemplateMapper.selectByCode(sendSms.getSmsTemplateCode());
        FieldContext fieldContext = bindCmp.getContextBean(FieldContext.class);
        String smsContent = StrUtil.format(smsTemplate.getContent(), fieldContext);
        log.info("smsContent: {}", smsContent);
    }

    List<String> parseTemplateContentParams(String content) {
        return ReUtil.findAllGroup1(PATTERN_PARAMS, content);
    }
}
