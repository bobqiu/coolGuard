package cn.wnhyang.coolGuard.vo;

import cn.wnhyang.coolGuard.vo.create.IndicatorCreateVO;
import lombok.Data;

/**
 * @author wnhyang
 * @date 2024/3/14
 **/
@Data
public class IndicatorVO extends IndicatorCreateVO {

    /**
     * 主键
     */
    private Long id;

    /**
     * 时间片
     */
    private Long timeSlice;

    /**
     * 指标值
     */
    private String value;

}
