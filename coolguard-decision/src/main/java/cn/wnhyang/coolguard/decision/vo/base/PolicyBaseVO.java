package cn.wnhyang.coolguard.decision.vo.base;

import cn.wnhyang.coolguard.decision.entity.Th;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @author wnhyang
 * @date 2024/4/17
 **/
@Data
public class PolicyBaseVO implements Serializable {

    @Serial
    private static final long serialVersionUID = -7548106664553347287L;

    /**
     * 策略名
     */
    @NotBlank(message = "策略名不能为空")
    private String name;

    /**
     * 策略阈值
     */
    private List<Th> thList;

    /**
     * 描述
     */
    private String description;
}
