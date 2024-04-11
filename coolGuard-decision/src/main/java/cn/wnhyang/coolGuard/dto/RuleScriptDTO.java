package cn.wnhyang.coolGuard.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 规则脚本表
 *
 * @author wnhyang
 * @since 2024/04/03
 */
@Data
public class RuleScriptDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 脚本应用名
     */
    private String applicationName;

    /**
     * 脚本id
     */
    private String scriptId;

    /**
     * 脚本名
     */
    private String scriptName;

    /**
     * 脚本数据
     */
    private String scriptData;

    /**
     * 脚本类型
     */
    private String scriptType;

    /**
     * 脚本语言
     */
    private String language;

    /**
     * 脚本状态
     */
    private Boolean enable;

    /**
     * 描述
     */
    private String description;
}
