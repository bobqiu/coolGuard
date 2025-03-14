package cn.wnhyang.coolguard.decision.vo.base;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author wnhyang
 * @date 2024/12/8
 **/
@Data
public class SmsTemplateBase implements Serializable {

    @Serial
    private static final long serialVersionUID = -1957761425919314569L;

    /**
     * 消息名
     */
    private String name;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 描述
     */
    private String description;
}
