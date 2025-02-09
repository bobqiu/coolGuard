package cn.wnhyang.coolGuard.vo;

import cn.wnhyang.coolGuard.vo.base.AccessBaseVO;
import lombok.Data;

import java.io.Serial;

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

}
