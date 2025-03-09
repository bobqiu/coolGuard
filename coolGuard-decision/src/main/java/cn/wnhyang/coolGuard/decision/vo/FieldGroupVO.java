package cn.wnhyang.coolGuard.decision.vo;

import cn.wnhyang.coolGuard.decision.vo.base.FieldGroupBaseVO;
import lombok.Data;

import java.io.Serial;

/**
 * @author wnhyang
 * @date 2024/3/14
 **/
@Data
public class FieldGroupVO extends FieldGroupBaseVO {

    @Serial
    private static final long serialVersionUID = -3827226919190224275L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 字段分组编码
     */
    private String code;

    /**
     * 是否为标准
     */
    private Boolean standard;

    /**
     * 组内字段数
     */
    private Long count;

}
