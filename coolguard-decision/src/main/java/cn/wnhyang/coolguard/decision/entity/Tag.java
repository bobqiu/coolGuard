package cn.wnhyang.coolguard.decision.entity;

import cn.wnhyang.coolguard.common.LabelValueAble;
import cn.wnhyang.coolguard.common.entity.LabelValue;
import cn.wnhyang.coolguard.mybatis.BaseDO;
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

/**
 * 标签表
 *
 * @author wnhyang
 * @since 2024/12/08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("de_tag")
public class Tag extends BaseDO implements LabelValueAble {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 标签编码
     */
    @TableField("code")
    private String code;

    /**
     * 标签名
     */
    @TableField("name")
    private String name;

    /**
     * 颜色
     */
    @TableField("color")
    private String color;

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
