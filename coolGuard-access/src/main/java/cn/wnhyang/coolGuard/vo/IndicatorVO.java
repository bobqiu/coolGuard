package cn.wnhyang.coolGuard.vo;

import cn.wnhyang.coolGuard.vo.base.IndicatorBaseVO;
import lombok.Data;

import java.io.Serial;

/**
 * @author wnhyang
 * @date 2024/3/14
 **/
@Data
public class IndicatorVO extends IndicatorBaseVO {

    @Serial
    private static final long serialVersionUID = -1533346951107805359L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 类型
     */
    private String type;

    /**
     * 计算字段
     */
    private String calcField;

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
     * 主字段
     */
    private String masterField;

    /**
     * 从字段
     */
    private String slaveFields;

    /**
     * 过滤脚本
     */
    private String filterScript;

    /**
     * 版本号
     */
    private Integer version;

    /**
     * 场景
     */
    private String scene;

    /**
     * 场景类型
     */
    private String sceneType;

    /**
     * 时间片
     */
    private Long timeSlice;

    /**
     * 指标值
     */
    private String value;

}
