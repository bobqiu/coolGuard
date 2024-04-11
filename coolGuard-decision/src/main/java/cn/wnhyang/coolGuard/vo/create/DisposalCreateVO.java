package cn.wnhyang.coolGuard.vo.create;

import lombok.Data;

import java.io.Serializable;

/**
 * 处置表
 *
 * @author wnhyang
 * @since 2024/04/03
 */
@Data
public class DisposalCreateVO implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 处置编码
     */
    private String code;

    /**
     * 处置名
     */
    private String name;

    /**
     * 描述
     */
    private String description;
}
