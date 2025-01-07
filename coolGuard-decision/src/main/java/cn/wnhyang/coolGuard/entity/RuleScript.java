package cn.wnhyang.coolGuard.entity;


import cn.wnhyang.coolGuard.pojo.BaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serial;

/**
 * 规则脚本表
 *
 * @author wnhyang
 * @since 2024/04/03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("de_rule_script")
public class RuleScript extends BaseDO {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 脚本应用名
     */
    @TableField("application_name")
    private String applicationName;

    /**
     * 脚本id
     */
    @TableField("script_id")
    private String scriptId;

    /**
     * 脚本名
     */
    @TableField("script_name")
    private String scriptName;

    /**
     * 脚本数据
     */
    @TableField("script_data")
    private String scriptData;

    /**
     * 脚本类型
     */
    @TableField("script_type")
    private String scriptType;

    /**
     * 脚本语言
     */
    @TableField("language")
    private String language;

    /**
     * 脚本状态
     */
    @TableField("enable")
    private Boolean enable;

    /**
     * 描述
     */
    @TableField("description")
    private String description;
}
