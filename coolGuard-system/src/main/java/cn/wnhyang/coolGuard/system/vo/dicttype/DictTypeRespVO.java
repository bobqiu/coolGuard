package cn.wnhyang.coolGuard.system.vo.dicttype;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author wnhyang
 * @date 2023/9/14
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DictTypeRespVO extends DictTypeBaseVO {

    private Long id;

    private String code;

    private Boolean standard;

}
