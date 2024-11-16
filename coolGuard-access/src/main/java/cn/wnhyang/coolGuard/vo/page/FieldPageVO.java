package cn.wnhyang.coolGuard.vo.page;

import cn.wnhyang.coolGuard.pojo.PageParam;
import lombok.Data;

import java.io.Serial;

/**
 * @author wnhyang
 * @date 2024/3/14
 **/
@Data
public class FieldPageVO extends PageParam {

    @Serial
    private static final long serialVersionUID = -4138518261130779757L;

    /**
     * 显示名
     */
    private String displayName;

    /**
     * 字段名
     */
    private String name;

    /**
     * 字段分组名
     */
    private String groupName;

    /**
     * 字段类型
     */
    private String type;

    /**
     * 是否标准字段
     */
    private Boolean standard;

    /**
     * 是否动态字段(0否1是)
     */
    private Boolean dynamic;
}
