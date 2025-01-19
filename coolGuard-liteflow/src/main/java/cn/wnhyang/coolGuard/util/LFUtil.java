package cn.wnhyang.coolGuard.util;

import cn.hutool.core.util.StrUtil;
import cn.wnhyang.coolGuard.entity.Cond;

/**
 * @author wnhyang
 * @date 2024/7/18
 **/
public class LFUtil {

    /**
     * if
     */
    public static final String IF_EL = "IF({},{},{});";

    /**
     * 并行EL，带上空组件
     */
    public static final String WHEN_EMPTY_NODE = "WHEN(e_cn);";

    /**
     * accessId
     */
    public static final String ACCESS_CHAIN = "A_C#{}";

    /**
     * indicatorId
     */
    public static final String INDICATOR_CHAIN = "I_C#{}";

    /**
     * policySetCode
     */
    public static final String POLICY_SET_CHAIN = "PS_C#{}";

    /**
     * 策略并行
     */
    public static final String P_FP = "P_FP";

    /**
     * 策略串行
     */
    public static final String P_F = "P_F";

    /**
     * ruleId
     */
    public static final String RULE_CHAIN = "R_C#{}";

    /**
     * empty普通组件
     */
    public static final String EMPTY_COMMON_NODE = "e_cn";

    /**
     * 设置字段组件
     */
    public static final String SET_FIELD = "setField";

    /**
     * 指标次数循环组件
     */
    public static final String INDICATOR_FOR_NODE = "i_fn";

    /**
     * 指标普通组件
     */
    public static final String INDICATOR_COMMON_NODE = "i_cn";

    /**
     * 指标true普通组件
     */
    public static final String INDICATOR_TRUE_COMMON_NODE = "i_tcn";

    /**
     * 指标false普通组件
     */
    public static final String INDICATOR_FALSE_COMMON_NODE = "i_fcn";

    /**
     * 策略集路由普通组件
     */
    public static final String POLICY_SET_COMMON_NODE = "ps_cn";

    /**
     * 策略普通组件
     */
    public static final String POLICY_COMMON_NODE = "p_cn";

    /**
     * 策略次数循环组件
     */
    public static final String POLICY_FOR_NODE = "p_fn";

    /**
     * 策略次数循环组件
     */
    public static final String POLICY_BREAK_NODE = "p_bn";

    /**
     * 条件普通组件
     */
    public static final String COND = "cond";

    /**
     * 条件普通组件
     */
    public static final String COND_LEAF = "cond_leaf";

    /**
     * 加入list组件
     */
    public static final String ADD_LIST_DATA = "addListData";

    /**
     * 加入标签组件
     */
    public static final String ADD_TAG = "addTag";

    /**
     * 发送消息组件
     */
    public static final String SEND_SMS = "sendSms";

    /**
     * 规则普通组件
     */
    public static final String RULE_COMMON_NODE = "r_cn";

    /**
     * 规则true普通组件
     */
    public static final String RULE_TRUE = "ruleTrue";

    /**
     * 规则false普通组件
     */
    public static final String RULE_FALSE = "ruleFalse";

    public static final String NODE_WITH_TAG = "{}.tag(\"{}\")";

    /**
     * @param nodeId 组件id
     * @param tag    标签
     * @return 带标签的组件
     */
    public static String getNodeWithTag(String nodeId, String tag) {
        return StrUtil.format(NODE_WITH_TAG, nodeId, tag);
    }

    public static String buildElWithData(String nodeId, String data) {
        return StrUtil.format("{}.data('\"{}\"')", nodeId, data);
    }

    public static String buildWhen(String... el) {
        return "when(" + StrUtil.join(",", el) + ");";
    }

    public static String buildCondEl(Cond cond) {
        return "cond.data('" + JsonUtil.toJsonString(cond) + "')";
    }

    public static void main(String[] args) throws Exception {

    }
}
