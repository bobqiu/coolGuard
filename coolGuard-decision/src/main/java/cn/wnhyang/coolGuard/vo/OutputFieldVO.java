package cn.wnhyang.coolGuard.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wnhyang
 * @date 2024/5/9
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OutputFieldVO {

    /**
     * 参数名
     */
    private String paramName;

    /**
     * 字段名，命名N/D_S/N/F/D/E/B_name
     * N普通字段，D动态字段
     * S/N/F/D/E/B字段类型
     */
    private String code;
}
