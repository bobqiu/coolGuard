package cn.wnhyang.coolGuard.decision.vo;

import cn.wnhyang.coolGuard.decision.entity.Cond;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 指标表版本表
 *
 * @author wnhyang
 * @since 2024/11/21
 */
@Data
public class IndicatorVersionVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 自增编号
     */
    private Long id;

    /**
     * 指标版本编码
     */
    private String code;

    /**
     * 指标名
     */
    private String name;

    /**
     * 最新
     */
    private Boolean latest;

    /**
     * 类型
     */
    private String type;

    /**
     * 计算字段
     */
    private String calcField;

    /**
     * 返回类型
     */
    private String returnType;

    /**
     * 返回取值方式
     */
    private String returnFlag;

    /**
     * 窗口大小
     */
    private String winSize;

    /**
     * 窗口类型
     */
    private String winType;

    /**
     * 窗口数量
     */
    private Integer winCount;

    /**
     * 时间片
     */
    private Long timeSlice;

    /**
     * 主字段
     */
    private String masterField;

    /**
     * 从字段
     */
    private List<String> slaveFields;

    /**
     * 计算脚本
     */
    private String computeScript;

    /**
     * 场景
     */
    private List<String> scenes;

    /**
     * 场景类型
     */
    private String sceneType;

    /**
     * 描述
     */
    private String description;

    /**
     * 版本号
     */
    private Integer version;

    /**
     * 版本描述
     */
    private String versionDesc;

    /**
     * 条件
     */
    private Cond cond;
}
