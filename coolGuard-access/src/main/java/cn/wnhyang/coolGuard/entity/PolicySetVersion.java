package cn.wnhyang.coolGuard.entity;

import cn.wnhyang.coolGuard.pojo.BasePO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 策略集表历史
 *
 * @author wnhyang
 * @since 2024/11/30
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
     * 应用名
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
    @TableField("status")
    private Boolean status;

    /**
     * 策略集链路
     */
    @TableField("chain")
    private String chain;

    /**
     * 描述
     */
    @TableField("description")
    private String description;

    /**
     * 版本号
     */
    @TableField("version")
    private Integer version;
}
