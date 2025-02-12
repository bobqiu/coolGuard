package cn.wnhyang.coolGuard.vo.page;

import cn.wnhyang.coolGuard.pojo.PageParam;
import lombok.Data;

import java.io.Serial;

/**
 * 策略版本表
 *
 * @author wnhyang
 * @since 2025/02/11
 */
@Data
public class PolicyVersionPageVO extends PageParam {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 编码
     */
    private String code;
}
