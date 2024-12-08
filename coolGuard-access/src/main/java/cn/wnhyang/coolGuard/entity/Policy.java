package cn.wnhyang.coolGuard.entity;

import cn.wnhyang.coolGuard.pojo.BasePO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serial;

/**
 * 策略表
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("de_policy")
public class Policy extends BasePO {

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
     * 描述
     */
    @TableField("description")
    private String description;
}
