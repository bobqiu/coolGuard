package cn.wnhyang.coolGuard.vo.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

/**
 * @author wnhyang
 * @date 2024/4/11
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestDynamicFieldScript implements Serializable {

    @Serial
    private static final long serialVersionUID = -793230133623515208L;

    private Map<String, Object> params;

    private String script;
}
