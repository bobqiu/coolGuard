package cn.wnhyang.coolGuard.vo.create;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

/**
 * @author wnhyang
 * @date 2024/4/11
 **/
@Data
public class TestDynamicFieldScript implements Serializable {

    @Serial
    private static final long serialVersionUID = -793230133623515208L;

    private Map<String, String> params;

    private String fieldName;

    private String script;
}
