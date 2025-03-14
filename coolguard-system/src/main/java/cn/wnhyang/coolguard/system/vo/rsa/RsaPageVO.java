package cn.wnhyang.coolguard.system.vo.rsa;

import cn.wnhyang.coolguard.common.pojo.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * @author wnhyang
 * @date 2023/10/17
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class RsaPageVO extends PageParam {

    @Serial
    private static final long serialVersionUID = -5139024472865142956L;
}
