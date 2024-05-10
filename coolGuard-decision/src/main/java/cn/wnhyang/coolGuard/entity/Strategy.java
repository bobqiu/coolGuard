package cn.wnhyang.coolGuard.entity;

import cn.wnhyang.coolGuard.pojo.BasePO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

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
@TableName("de_strategy")
public class Strategy extends BasePO {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * chain名
     */
    @TableField("chain_name")
    private String chainName;

    /**
     * 策略集id
     */
    @TableField("strategy_set_id")
    private Long strategySetId;

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
    private Integer status;

    /**
     * 描述
     */
    @TableField("description")
    private String description;
}
