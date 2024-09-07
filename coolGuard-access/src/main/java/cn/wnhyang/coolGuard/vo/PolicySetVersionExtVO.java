package cn.wnhyang.coolGuard.vo;

import cn.wnhyang.coolGuard.vo.create.PolicySetVersionExtCreateVO;
import lombok.Data;

/**
 * 策略集版本扩展表
 *
 * @author wnhyang
 * @since 2024/08/29
 */
@Data
public class PolicySetVersionExtVO extends PolicySetVersionExtCreateVO {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;
}
