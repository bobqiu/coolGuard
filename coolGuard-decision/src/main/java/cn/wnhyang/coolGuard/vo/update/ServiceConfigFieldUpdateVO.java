package cn.wnhyang.coolGuard.vo.update;

import cn.wnhyang.coolGuard.vo.create.ServiceConfigFieldCreateVO;
import lombok.Data;

/**
 * 服务配置字段表
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@Data
public class ServiceConfigFieldUpdateVO extends ServiceConfigFieldCreateVO {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;
}
