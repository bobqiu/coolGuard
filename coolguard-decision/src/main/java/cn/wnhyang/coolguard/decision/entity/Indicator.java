package cn.wnhyang.coolguard.decision.entity;

import cn.wnhyang.coolguard.common.LabelValueAble;
import cn.wnhyang.coolguard.common.entity.LabelValue;
import cn.wnhyang.coolguard.mybatis.BaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.util.List;

/**
 * 指标表
 *
 * @author wnhyang
 * @since 2024/03/14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "de_indicator", autoResultMap = true)
public class Indicator extends BaseDO implements LabelValueAble {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 自增编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 指标编码
     */
    @TableField("code")
    private String code;

    /**
     * 指标名
     */
    @TableField("name")
    private String name;

    /**
     * 状态
     */
    @TableField("publish")
    private Boolean publish;

    /**
     * 类型
     */
    @TableField("type")
    private String type;

    /**
     * 计算字段，必须为数值和小数类
     */
    @TableField("calc_field")
    private String calcField;

    /**
     * 返回类型
     */
    @TableField("return_type")
    private String returnType;

    /**
     * earliest latest
     * 返回取值方式
     */
    @TableField("return_flag")
    private String returnFlag;

    /**
     * 窗口大小
     */
    @TableField("win_size")
    private String winSize;

    /**
     * 窗口类型
     */
    @TableField("win_type")
    private String winType;

    /**
     * 窗口数量
     */
    @TableField("win_count")
    private Integer winCount;

    /**
     * 时间片
     */
    @TableField("time_slice")
    private Long timeSlice;

    /**
     * 主字段 只能是字符类
     */
    @TableField("master_field")
    private String masterField;

    /**
     * 从字段 只能是字符类
     */
    @TableField(value = "slave_fields", typeHandler = JacksonTypeHandler.class)
    private List<String> slaveFields;

    /**
     * 过滤脚本
     */
    @TableField("compute_script")
    private String computeScript;

    /**
     * 场景，多个
     * appName
     * policySetCode
     */
    @TableField(value = "scenes", typeHandler = JacksonTypeHandler.class)
    private List<String> scenes;

    /**
     * 场景类型
     */
    @TableField("scene_type")
    private String sceneType;

    /**
     * 条件
     */
    @TableField(value = "cond", typeHandler = JacksonTypeHandler.class)
    private Cond cond;

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
