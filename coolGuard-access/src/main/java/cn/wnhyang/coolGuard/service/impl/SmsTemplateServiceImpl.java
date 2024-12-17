package cn.wnhyang.coolGuard.service.impl;

import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import cn.wnhyang.coolGuard.context.FieldContext;
import cn.wnhyang.coolGuard.convert.SmsTemplateConvert;
import cn.wnhyang.coolGuard.entity.Action;
import cn.wnhyang.coolGuard.entity.SmsTemplate;
import cn.wnhyang.coolGuard.mapper.SmsTemplateMapper;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.SmsTemplateService;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

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

    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS, nodeId = LFUtil.SEND_SMS, nodeType = NodeTypeEnum.COMMON, nodeName = "加入名单组件")
    public void sendSms(NodeComponent bindCmp) {
        // TODO 完善
        Action.SendSms sendSms = bindCmp.getCmpData(Action.SendSms.class);
        SmsTemplate smsTemplate = smsTemplateMapper.selectByCode(sendSms.getSmsTemplateCode());
        FieldContext fieldContext = bindCmp.getContextBean(FieldContext.class);
        Map<String, Object> map = new HashMap<>();
        for (String param : smsTemplate.getParams()) {
            map.put(param, fieldContext.getStringData(param));
        }
        String smsContent = StrUtil.format(smsTemplate.getContent(), map);
        log.info("smsContent: {}", smsContent);
    }

    List<String> parseTemplateContentParams(String content) {
        return ReUtil.findAllGroup1(PATTERN_PARAMS, content);
    }
}
