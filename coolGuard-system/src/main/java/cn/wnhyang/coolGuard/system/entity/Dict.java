package cn.wnhyang.coolGuard.system.entity;

import cn.wnhyang.coolGuard.LabelValueAble;
import cn.wnhyang.coolGuard.entity.LabelValue;
import cn.wnhyang.coolGuard.pojo.BaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.io.Serial;
import java.util.List;

/**
 * 字典表
 *
 * @author wnhyang
 * @since 2025/01/03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "sys_dict", autoResultMap = true)
public class Dict extends BaseDO implements LabelValueAble {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 字典id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 字典标签
     */
    @TableField("label")
    private String label;

    /**
     * 字典值
     */
    @TableField("value")
    private String value;

    /**
     * 字典数据
     */
    @TableField(value = "data", typeHandler = JacksonTypeHandler.class)
    private List<DictData> data;

    /**
     * 标准
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
        return new LabelValue(id, label, value);
    }
}
