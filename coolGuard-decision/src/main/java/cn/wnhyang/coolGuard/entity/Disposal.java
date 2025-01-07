package cn.wnhyang.coolGuard.entity;


import cn.wnhyang.coolGuard.LabelValueAble;
import cn.wnhyang.coolGuard.pojo.BaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.io.Serial;

/**
 * 处置表
 *
 * @author wnhyang
 * @since 2024/04/03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("de_disposal")
public class Disposal extends BaseDO implements LabelValueAble {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 处置编码
     */
    @TableField("code")
    private String code;

    /**
     * 处置名
     */
    @TableField("name")
    private String name;

    /**
     * 等级
     */
    @TableField("grade")
    private Integer grade;

    /**
     * 颜色
     */
    @TableField("color")
    private String color;

    /**
     * 是否为标准
     */
    @TableField("standard")
    private Boolean standard;

    /**
     * 描述
     */
    @TableField("description")
    private String description;

    @Override
    @JsonIgnore
    public LabelValue getLabelValue() {
        return new LabelValue(id, name, code);
    }
}
