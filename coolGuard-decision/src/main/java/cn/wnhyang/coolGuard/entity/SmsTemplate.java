package cn.wnhyang.coolGuard.entity;

import cn.wnhyang.coolGuard.LabelValueAble;
import cn.wnhyang.coolGuard.pojo.BaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.util.List;

/**
 * 消息模版表
 *
 * @author wnhyang
 * @since 2024/12/08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "de_sms_template", autoResultMap = true)
public class SmsTemplate extends BaseDO implements LabelValueAble {

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
    @TableField(value = "params", typeHandler = JacksonTypeHandler.class)
    private List<String> params;

    /**
     * 描述
     */
    @TableField("description")
    private String description;

    @Override
    @JsonIgnore
    public LabelValue getLabelValue() {
        return new LabelValue(id, name, code);
    }
}
