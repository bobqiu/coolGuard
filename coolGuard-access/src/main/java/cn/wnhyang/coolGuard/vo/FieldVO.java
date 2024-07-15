package cn.wnhyang.coolGuard.vo;

import cn.wnhyang.coolGuard.vo.create.FieldCreateVO;
import lombok.Data;

/**
 * @author wnhyang
 * @date 2024/3/14
 **/
@Data
public class FieldVO extends FieldCreateVO {

    /**
     * 主键
     */
    private Long id;

    /**
     * 是否标准字段，是：不可以删除
     */
    private Boolean standard;
}
