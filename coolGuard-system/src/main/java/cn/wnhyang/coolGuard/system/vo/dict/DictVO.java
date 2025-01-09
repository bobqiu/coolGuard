package cn.wnhyang.coolGuard.system.vo.dict;

import lombok.Data;

import java.io.Serial;

/**
 * 字典表
 *
 * @author wnhyang
 * @since 2025/01/03
 */
@Data
public class DictVO extends DictCreateVO {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 字典id
     */
    private Long id;

    /**
     * 标准
     */
    private Boolean standard;
}
