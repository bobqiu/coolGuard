package cn.wnhyang.coolGuard.vo.update;

import cn.wnhyang.coolGuard.vo.create.FieldCreateVO;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author wnhyang
 * @date 2024/3/14
 **/
@Data
public class FieldUpdateVO extends FieldCreateVO {

    /**
     * 主键
     */
    @NotNull(message = "id不能为空")
    private Long id;
}
