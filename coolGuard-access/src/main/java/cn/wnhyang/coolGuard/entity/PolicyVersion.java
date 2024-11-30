package cn.wnhyang.coolGuard.entity;

import cn.wnhyang.coolGuard.pojo.BasePO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 策略版本表
 *
 * @author wnhyang
 * @since 2024/08/29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("de_policy_version")
public class PolicyVersion extends BasePO {

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
     * 策略状态，应该包含开启、关闭、待发布
     */
    @TableField("status")
    private Boolean status;

    /**
     * 策略状态
     */
    @TableField("version")
    private Integer version;

    /**
     * 描述
     */
    @TableField("description")
    private String description;
}
