package cn.wnhyang.coolGuard.system.vo.dict;

import cn.wnhyang.coolGuard.pojo.PageParam;
import lombok.Data;

import java.io.Serial;

/**
 * 字典表
 *
 * @author wnhyang
 * @since 2025/01/03
 */
@Data
public class DictPageVO extends PageParam {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 字典id
     */
    private Long id;

    /**
     * 字典标签
     */
    private String label;

    /**
     * 字典值
     */
    private String value;
}
