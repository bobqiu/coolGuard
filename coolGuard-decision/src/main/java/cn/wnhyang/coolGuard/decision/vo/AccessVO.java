package cn.wnhyang.coolGuard.decision.vo;

import cn.wnhyang.coolGuard.decision.vo.base.AccessBaseVO;
import lombok.Data;

import java.io.Serial;
import java.util.Map;

/**
 * @author wnhyang
 * @date 2024/3/14
 **/
@Data
public class AccessVO extends AccessBaseVO {

    @Serial
    private static final long serialVersionUID = -6121337194207258432L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 接入编码
     */
    private String code;

    /**
     * 测试参数
     */
    private Map<String, String> testParams;

}
