package cn.wnhyang.coolGuard.vo.create;

import com.ql.util.express.DefaultContext;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

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

    private DefaultContext<String, Object> context;

    private String script;
}
