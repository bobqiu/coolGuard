package cn.wnhyang.coolGuard.system.vo.dictdata;

import cn.wnhyang.coolGuard.pojo.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * @author wnhyang
 * @date 2023/9/15
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class DictDataPageVO extends PageParam {

    @Serial
    private static final long serialVersionUID = 7765456791884144537L;

    private String name;

    private String code;

    private String typeCode;

    private Boolean status;
}
