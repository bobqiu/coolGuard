package cn.wnhyang.coolGuard.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 策略集版本扩展表
 *
 * @author wnhyang
 * @since 2024/08/29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("de_policy_set_version_ext")
public class PolicySetVersionExt implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 策略流
     */
    @TableField("policy_flow")
    private String policyFlow;

    /**
     * 规则信息
     */
    @TableField("rule_info")
    private String ruleInfo;
}
