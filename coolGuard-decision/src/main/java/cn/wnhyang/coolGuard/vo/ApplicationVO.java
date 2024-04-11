package cn.wnhyang.coolGuard.vo;

import cn.wnhyang.coolGuard.vo.create.ApplicationCreateVO;
import lombok.Data;

/**
 * 应用表
 *
 * @author wnhyang
 * @since 2024/04/03
 */
@Data
public class ApplicationVO extends ApplicationCreateVO {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;
}
