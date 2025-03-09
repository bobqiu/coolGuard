package cn.wnhyang.coolGuard.system.vo.dicttype;

import cn.wnhyang.coolGuard.common.pojo.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * @author wnhyang
 * @date 2023/9/14
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class DictTypePageVO extends PageParam {

    @Serial
    private static final long serialVersionUID = -2268644414650536395L;

    private String name;

    private String code;

}
