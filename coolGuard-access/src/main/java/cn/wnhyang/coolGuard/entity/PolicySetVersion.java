package cn.wnhyang.coolGuard.entity;

import cn.wnhyang.coolGuard.pojo.BasePO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 策略集版本表
 *
 * @author wnhyang
 * @since 2024/08/29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("de_policy_set_version")
public class PolicySetVersion extends BasePO {

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
     * 策略id
     */
    @TableField("policy_code")
    private String policyCode;

    /**
     * 策略状态
     */
    @TableField("version")
    private Integer version;
}
