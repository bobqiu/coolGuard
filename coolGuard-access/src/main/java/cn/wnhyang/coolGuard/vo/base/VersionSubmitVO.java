package cn.wnhyang.coolGuard.vo.base;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author wnhyang
 * @date 2024/11/21
 **/
@Data
public class VersionSubmitVO implements Serializable {

    @Serial
    private static final long serialVersionUID = -603827310071440471L;

    private Long id;

    private String versionDesc;
}
