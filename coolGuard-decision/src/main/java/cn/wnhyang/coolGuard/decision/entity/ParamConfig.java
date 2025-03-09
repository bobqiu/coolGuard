package cn.wnhyang.coolGuard.decision.entity;

import cn.wnhyang.coolGuard.common.entity.LabelValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author wnhyang
 * @date 2025/3/6
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParamConfig {

    private String paramName;

    private Boolean required;

    private String fieldCode;

    /**
     * 字段名
     */
    private String name;

    /**
     * 字段类型
     */
    private String type;

    /**
     * 字段信息
     */
    private List<LabelValue> info;

    /**
     * 是否动态字段(0否1是)
     */
    private Boolean dynamic;

    /**
     * 动态字段脚本
     */
    private String script;


}
