package cn.wnhyang.coolGuard.entity;

import cn.wnhyang.coolGuard.pojo.BasePO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serial;

/**
 * 指标表
 *
 * @author wnhyang
 * @since 2024/03/14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("de_indicator")
public class Indicator extends BasePO {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 自增编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 指标名
     */
    @TableField("name")
    private String name;

    /**
     * 状态
     */
    @TableField("status")
    private Boolean status;

    /**
     * chain名
     */
    @TableField("chain_name")
    private String chainName;

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
     * 主字段
     */
    @TableField("master_field")
    private String masterField;

    /**
     * 从字段
     */
    @TableField("slave_fields")
    private String slaveFields;

    /**
     * 过滤脚本
     */
    @TableField("compute_script")
    private String computeScript;

    /**
     * 版本号
     */
    @TableField("version")
    private Integer version;

    /**
     * 场景
     */
    @TableField("scene")
    private String scene;

    /**
     * 场景类型
     */
    @TableField("scene_type")
    private String sceneType;

    /**
     * 描述
     */
    @TableField("description")
    private String description;
}
