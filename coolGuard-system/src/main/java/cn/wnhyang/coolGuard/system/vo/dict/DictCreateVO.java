package cn.wnhyang.coolGuard.system.vo.dict;

import cn.wnhyang.coolGuard.system.entity.DictData;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 字典表
 *
 * @author wnhyang
 * @since 2025/01/03
 */
@Data
public class DictCreateVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 字典标签
     */
    @NotBlank(message = "字典标签不能为空")
    private String label;

    /**
     * 字典值
     */
    @NotBlank(message = "字典值不能为空")
    private String value;

    /**
     * 字典数据
     */
    @Size(min = 1, message = "字典数据不能为空")
    private List<DictData> data;

    /**
     * 描述
     */
    private String description;
}
