package cn.wnhyang.coolGuard.vo;

import cn.wnhyang.coolGuard.vo.create.PolicySetVersionCreateVO;
import lombok.Data;

/**
 * 策略集版本表
 *
 * @author wnhyang
 * @since 2024/08/29
 */
@Data
public class PolicySetVersionVO extends PolicySetVersionCreateVO {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;
}
