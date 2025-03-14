package cn.wnhyang.coolguard.decision.vo.update;

import cn.wnhyang.coolguard.decision.vo.base.SmsTemplateBase;
import lombok.Data;

import java.io.Serial;

/**
 * 消息模版表
 *
 * @author wnhyang
 * @since 2024/12/08
 */
@Data
public class SmsTemplateUpdateVO extends SmsTemplateBase {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;
}
