package cn.wnhyang.coolGuard.vo.update;

import cn.wnhyang.coolGuard.vo.base.ServiceConfigFieldBaseVO;
import lombok.Data;

import java.io.Serial;

/**
 * 服务配置字段表
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@Data
public class ServiceConfigFieldUpdateVO extends ServiceConfigFieldBaseVO {

    @Serial
    private static final long serialVersionUID = 8656278247425993521L;

    /**
     * 主键
     */
    private Long id;
}
