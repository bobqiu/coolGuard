package cn.wnhyang.coolguard.decision.service;

import cn.wnhyang.coolguard.common.entity.LabelValue;
import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.decision.entity.Action;
import cn.wnhyang.coolguard.decision.entity.SmsTemplate;
import cn.wnhyang.coolguard.decision.vo.create.SmsTemplateCreateVO;
import cn.wnhyang.coolguard.decision.vo.page.SmsTemplatePageVO;
import cn.wnhyang.coolguard.decision.vo.update.SmsTemplateUpdateVO;

import java.util.List;

/**
 * 消息模版表 服务类
 *
 * @author wnhyang
 * @since 2024/12/08
 */
public interface SmsTemplateService {

    /**
     * 新建
     *
     * @param createVO 新建VO
     * @return id
     */
    Long create(SmsTemplateCreateVO createVO);

    /**
     * 更新
     *
     * @param updateVO 更新VO
     */
    void update(SmsTemplateUpdateVO updateVO);

    /**
     * 删除
     *
     * @param id id
     */
    void delete(Long id);

    /**
     * 查询单个
     *
     * @param id id
     * @return po
     */
    SmsTemplate get(Long id);

    /**
     * 分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    PageResult<SmsTemplate> page(SmsTemplatePageVO pageVO);

    /**
     * 获取 lvList
     *
     * @return lvList
     */
    List<LabelValue> getLabelValueList();

    /**
     * 发送短信
     *
     * @param sendSms 发送短信
     */
    void sendSms(Action.SendSms sendSms);
}
