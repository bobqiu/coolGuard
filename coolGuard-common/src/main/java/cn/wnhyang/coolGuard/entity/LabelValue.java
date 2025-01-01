package cn.wnhyang.coolGuard.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wnhyang
 * @date 2024/11/19
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LabelValue {

    private String label;

    private String value;
}
