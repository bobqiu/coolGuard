package cn.wnhyang.coolGuard.vo;

import cn.wnhyang.coolGuard.entity.LabelValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author wnhyang
 * @date 2024/3/6
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InputFieldVO {

    /**
     * 参数名
     */
    private String paramName;

    /**
     * 是否必须
     */
    private Boolean required;

    /**
     * 主键
     */
    private Long id;

    /**
     * 字段名
     */
    private String name;

    /**
     * 字段编码，命名N/D_S/N/F/D/E/B_name
     * N普通字段，D动态字段
     * S/N/F/D/E/B字段类型
     */
    private String code;

    /**
     * 字段分组
     */
    private String groupCode;

    /**
     * 是否标准字段
     */
    private Boolean standard;

    /**
     * 字段类型
     */
    private String type;

    /**
     * 字段信息
     */
    private List<LabelValue> info;

    /**
     * 描述
     */
    private String description;

    /**
     * 默认值
     */
    private String defaultValue;

    /**
     * 是否动态字段(0否1是)
     */
    private Boolean dynamic;

    /**
     * 动态字段脚本
     */
    private String script;

}
