package cn.wnhyang.coolGuard.vo.create;

import cn.wnhyang.coolGuard.vo.base.ListSetBaseVO;
import lombok.Data;

import java.io.Serial;

/**
 * 名单集表
 *
 * @author wnhyang
 * @since 2024/05/28
 */
@Data
public class ListSetCreateVO extends ListSetBaseVO {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 名单集编码
     */
    private String code;

}
