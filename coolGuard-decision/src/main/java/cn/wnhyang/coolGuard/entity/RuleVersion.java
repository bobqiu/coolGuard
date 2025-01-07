package cn.wnhyang.coolGuard.entity;

import cn.wnhyang.coolGuard.pojo.BaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;

/**
 * 规则版本表
 *
 * @author wnhyang
 * @since 2024/08/29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "de_rule_version", autoResultMap = true)
public class RuleVersion extends BaseDO {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 规则编码
     */
    @TableField("code")
    private String code;

    /**
     * 条件
     */
    @TableField(value = "rule", typeHandler = JacksonTypeHandler.class)
    private Rule rule;

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
}
