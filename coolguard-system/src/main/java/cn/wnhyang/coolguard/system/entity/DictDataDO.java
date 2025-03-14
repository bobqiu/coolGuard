package cn.wnhyang.coolguard.system.entity;

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
 * 字典数据表
 *
 * @author wnhyang
 * @since 2023/09/13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_dict_data")
public class DictDataDO extends BaseDO implements LabelValueAble {

    @Serial
    private static final long serialVersionUID = 2306681901836679890L;

    /**
     * 字典数据主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 字典排序
     */
    @TableField("sort")
    private Integer sort;

    /**
     * 字典标签
     */
    @TableField("name")
    private String name;

    /**
     * 字典键值
     */
    @TableField("code")
    private String code;

    /**
     * 字典类型
     */
    @TableField("type_code")
    private String typeCode;

    /**
     * 字典标签颜色
     */
    @TableField("color")
    private String color;

    /**
     * 状态（0正常 1停用）
     */
    @TableField("status")
    private Boolean status;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    @Override
    @JsonIgnore
    public LabelValue getLabelValue() {
        return new LabelValue(id, name, code);
    }
}
