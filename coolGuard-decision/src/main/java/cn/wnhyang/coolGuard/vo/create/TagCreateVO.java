package cn.wnhyang.coolGuard.vo.create;

import cn.wnhyang.coolGuard.vo.base.TagBaseVO;
import lombok.Data;

import java.io.Serial;

/**
 * 标签表
 *
 * @author wnhyang
 * @since 2024/12/08
 */
@Data
public class TagCreateVO extends TagBaseVO {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 标签编码
     */
    private String code;

}
