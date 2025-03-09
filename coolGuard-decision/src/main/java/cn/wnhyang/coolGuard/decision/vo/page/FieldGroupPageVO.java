package cn.wnhyang.coolGuard.decision.vo.page;

import cn.wnhyang.coolGuard.common.pojo.PageParam;
import lombok.Data;

import java.io.Serial;

/**
 * @author wnhyang
 * @date 2024/3/14
 **/
@Data
public class FieldGroupPageVO extends PageParam {

    @Serial
    private static final long serialVersionUID = -4138518261130779757L;

    /**
     * 字段分组名
     */
    private String name;

    /**
     * 字段分组编码
     */
    private String code;

}
