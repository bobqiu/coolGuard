package cn.wnhyang.coolguard.decision.entity;

import cn.wnhyang.coolguard.common.entity.LabelValue;
import cn.wnhyang.coolguard.mybatis.BaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.util.List;

/**
 * 字段表
 *
 * @author wnhyang
 * @since 2024/03/14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "de_field", autoResultMap = true)
public class Field extends BaseDO {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 字段名
     */
    @TableField("name")
    private String name;

    /**
     * 字段编码，命名N/D_S/N/F/D/E/B_name
     * N普通字段，D动态字段
     * S/N/F/D/E/B字段类型
     */
    @TableField("code")
    private String code;

    /**
     * 字段分组
     */
    @TableField("group_code")
    private String groupCode;

    /**
     * 是否标准字段
     */
    @TableField("standard")
    private Boolean standard;

    /**
     * 字段类型
     */
    @TableField("type")
    private String type;

    /**
     * 字段信息
     */
    @TableField(value = "info", typeHandler = JacksonTypeHandler.class)
    private List<LabelValue> info;

    /**
     * 描述
     */
    @TableField("description")
    private String description;

    /**
     * 默认值
     */
    @TableField("default_value")
    private String defaultValue;

    /**
     * 是否动态字段(0否1是)
     */
    @TableField("dynamic")
    private Boolean dynamic;

    /**
     * 动态字段脚本
     */
    @TableField("script")
    private String script;
}
