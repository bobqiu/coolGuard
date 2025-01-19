package cn.wnhyang.coolGuard.system.vo.param;

import cn.wnhyang.coolGuard.pojo.PageParam;
import lombok.Data;

import java.io.Serial;

/**
 * 参数表
 *
 * @author wnhyang
 * @since 2025/01/07
 */
@Data
public class ParamPageVO extends PageParam {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 参数标签
     */
    private String name;

    /**
     * 参数值
     */
    private String code;

    /**
     * 参数类型
     */
    private String type;

    /**
     * 参数数据
     */
    private String data;

}
