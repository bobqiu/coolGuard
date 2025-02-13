package cn.wnhyang.coolGuard.entity;

import cn.wnhyang.coolGuard.pojo.BaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;

/**
 * 策略集表版本
 *
 * @author wnhyang
 * @since 2024/11/30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("de_policy_set_version")
public class PolicySetVersion extends BaseDO {

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
     * 策略集链
     */
    @TableField("chain")
    private String chain;

    /**
     * 描述
     */
    @TableField("description")
    private String description;

    /**
     * 最新
     */
    @TableField("latest")
    private Boolean latest;

    /**
     * 版本号
     */
    @TableField("version")
    private Integer version;

    /**
     * 版本描述
     */
    @TableField("version_desc")
    private String versionDesc;
}
