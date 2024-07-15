package cn.wnhyang.coolGuard.vo.update;

import cn.wnhyang.coolGuard.vo.create.IndicatorCreateVO;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author wnhyang
 * @date 2024/3/14
 **/
@Data
public class IndicatorUpdateVO extends IndicatorCreateVO {

    /**
     * 主键
     */
    @NotNull(message = "主键不能为空")
    private Long id;
}
