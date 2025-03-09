package cn.wnhyang.coolGuard.decision.entity;

import cn.wnhyang.coolGuard.common.LabelValueAble;
import cn.wnhyang.coolGuard.common.entity.LabelValue;
import cn.wnhyang.coolGuard.mybatis.BaseDO;
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
 * 策略表
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "de_policy", autoResultMap = true)
public class Policy extends BaseDO implements LabelValueAble {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 策略集id
     */
    @TableField("policy_set_code")
    private String policySetCode;

    /**
     * 策略编码
     */
    @TableField("code")
    private String code;

    /**
     * 策略名
     */
    @TableField("name")
    private String name;

    /**
     * 策略模式
     */
    @TableField("mode")
    private String mode;

    /**
     * 策略阈值
     */
    @TableField(value = "th_list", typeHandler = JacksonTypeHandler.class)
    private List<Th> thList;

    /**
     * 描述
     */
    @TableField("description")
    private String description;

    /**
     * 策略状态
     */
    @TableField("publish")
    private Boolean publish;

    @Override
    @JsonIgnore
    public LabelValue getLabelValue() {
        return new LabelValue(id, name, code);
    }
}
