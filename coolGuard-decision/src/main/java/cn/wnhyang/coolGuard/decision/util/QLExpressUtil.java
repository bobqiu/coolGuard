package cn.wnhyang.coolGuard.decision.util;

import com.ql.util.express.ExpressRunner;
import com.ql.util.express.IExpressContext;

/**
 * @author wnhyang
 * @date 2024/12/17
 **/
public class QLExpressUtil {

    private static final ExpressRunner EXPRESS_RUNNER;

    static {
        EXPRESS_RUNNER = new ExpressRunner();
    }

    public static Object execute(String express, IExpressContext<String, Object> context) throws Exception {
        return EXPRESS_RUNNER.execute(express, context, null, true, false);
    }
}
