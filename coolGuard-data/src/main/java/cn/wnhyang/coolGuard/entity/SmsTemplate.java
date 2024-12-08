package cn.wnhyang.coolGuard.entity;

import cn.wnhyang.coolGuard.pojo.BasePO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serial;

/**
 * 消息模版表
 *
 * @author wnhyang
 * @since 2024/12/08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("de_sms_template")
public class SmsTemplate extends BasePO {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 消息编码
     */
    @TableField("code")
    private String code;

    /**
     * 消息名
     */
    @TableField("name")
    private String name;

    /**
     * 消息内容
     */
    @TableField("content")
    private String content;

    /**
     * 消息参数
     */
    @TableField("params")
    private String params;

    /**
     * 描述
     */
    @TableField("description")
    private String description;
}
