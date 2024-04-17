package cn.wnhyang.coolGuard.vo;

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
public class ServiceConfigFieldVO extends ServiceConfigFieldBaseVO {

    @Serial
    private static final long serialVersionUID = 7303993433636717669L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 服务id
     */
    private Long serviceConfigId;
}
