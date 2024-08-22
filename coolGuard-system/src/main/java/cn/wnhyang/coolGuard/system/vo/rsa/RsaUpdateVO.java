package cn.wnhyang.coolGuard.system.vo.rsa;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wnhyang
 * @date 2023/10/17
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class RsaUpdateVO extends RsaCreateVO {

    /**
     * 密钥主键
     */
    @NotNull(message = "密钥编号不能为空")
    private Long id;

}
