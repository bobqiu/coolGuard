package cn.wnhyang.coolGuard.decision.vo.page;

import cn.wnhyang.coolGuard.common.pojo.PageParam;
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
     * 字段名
     */
    private String name;

    /**
     * 字段分组编码
     */
    private String code;

    /**
     * 字段分组名
     */
    private String groupCode;

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
