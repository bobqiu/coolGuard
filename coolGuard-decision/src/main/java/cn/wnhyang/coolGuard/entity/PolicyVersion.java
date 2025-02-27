package cn.wnhyang.coolGuard.entity;

import cn.wnhyang.coolGuard.LabelValueAble;
import cn.wnhyang.coolGuard.pojo.BaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.util.List;

/**
 * 策略版本表
 *
 * @author wnhyang
 * @since 2025/02/11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("de_policy_version")
public class PolicyVersion extends BaseDO implements LabelValueAble {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 策略集编码
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
     * 阈值表
     */
    @TableField("th_list")
    private List<Th> thList;

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

    @Override
    @JsonIgnore
    public LabelValue getLabelValue() {
        return new LabelValue(id, name, code);
    }
}
