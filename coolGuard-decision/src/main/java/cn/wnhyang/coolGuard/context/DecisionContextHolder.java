package cn.wnhyang.coolGuard.context;

import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * @author wnhyang
 * @date 2025/3/5
 **/
public class DecisionContextHolder {

    private static final TransmittableThreadLocal<FieldContext> FIELD_CONTEXT = new TransmittableThreadLocal<>();

    public static FieldContext getFieldContext() {
        return FIELD_CONTEXT.get();
    }

    public static void setFieldContext(FieldContext context) {
        FIELD_CONTEXT.set(context);
    }

    public static void removeFieldContext() {
        FIELD_CONTEXT.remove();
    }

    private static final TransmittableThreadLocal<IndicatorContext> INDICATOR_CONTEXT = new TransmittableThreadLocal<>();

    public static IndicatorContext getIndicatorContext() {
        return INDICATOR_CONTEXT.get();
    }

    public static void setIndicatorContext(IndicatorContext context) {
        INDICATOR_CONTEXT.set(context);
    }

    public static void removeIndicatorContext() {
        INDICATOR_CONTEXT.remove();
    }


    private static final TransmittableThreadLocal<PolicyContext> POLICY_CONTEXT = new TransmittableThreadLocal<>();

    public static PolicyContext getPolicyContext() {
        return POLICY_CONTEXT.get();
    }

    public static void setPolicyContext(PolicyContext context) {
        POLICY_CONTEXT.set(context);
    }

    public static void removePolicyContext() {
        POLICY_CONTEXT.remove();
    }

    private static final TransmittableThreadLocal<EventContext> EVENT_CONTEXT = new TransmittableThreadLocal<>();

    public static EventContext getEventContext() {
        return EVENT_CONTEXT.get();
    }

    public static void setEventContext(EventContext context) {
        EVENT_CONTEXT.set(context);
    }

    public static void removeEventContext() {
        EVENT_CONTEXT.remove();
    }

    public static void removeAll() {
        removeFieldContext();
        removeIndicatorContext();
        removePolicyContext();
        removeEventContext();
    }

}
