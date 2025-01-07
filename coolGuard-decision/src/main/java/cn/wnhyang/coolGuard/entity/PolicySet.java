package cn.wnhyang.coolGuard.entity;

import cn.wnhyang.coolGuard.LabelValueAble;
import cn.wnhyang.coolGuard.pojo.BaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.io.Serial;

/**
 * 策略集表
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("de_policy_set")
public class PolicySet extends BaseDO implements LabelValueAble {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * app名
     */
    @TableField("app_name")
    private String appName;

    /**
     * 策略集编码
     */
    @TableField("code")
    private String code;

    /**
     * 策略集名
     */
    @TableField("name")
    private String name;

    /**
     * 策略集状态
     */
    @TableField("publish")
    private Boolean publish;

    /**
     * 策略集链
     */
    @TableField("chain")
    private String chain;

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
