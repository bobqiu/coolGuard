package cn.wnhyang.coolguard.decision.vo.page;

import cn.wnhyang.coolguard.common.pojo.PageParam;
import lombok.Data;

import java.io.Serial;

/**
 * @author wnhyang
 * @date 2024/3/14
 **/
@Data
public class AccessPageVO extends PageParam {

    @Serial
    private static final long serialVersionUID = -4138518261130779757L;

    /**
     * 接入名
     */
    private String name;

    /**
     * 接入编码
     */
    private String code;

}
