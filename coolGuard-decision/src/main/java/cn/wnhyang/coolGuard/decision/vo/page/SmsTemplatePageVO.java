package cn.wnhyang.coolGuard.decision.vo.page;

import cn.wnhyang.coolGuard.common.pojo.PageParam;
import lombok.Data;

import java.io.Serial;

/**
 * 消息模版表
 *
 * @author wnhyang
 * @since 2024/12/08
 */
@Data
public class SmsTemplatePageVO extends PageParam {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 消息编码
     */
    private String code;

    /**
     * 消息名
     */
    private String name;
}
