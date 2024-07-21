package cn.wnhyang.coolGuard.util;

import cn.hutool.core.util.StrUtil;

/**
 * @author wnhyang
 * @date 2024/7/18
 **/
public class LFUtil {

    public static final String THEN = "THEN()";

    public static final String WHEN = "WHEN()";

    /**
     * 串行EL
     */
    public static final String THEN_EL = "THEN({});";

    /**
     * 并行EL
     */
    public static final String WHEN_EL = "WHEN({});";

    /**
     * 串行EL，带上空节点
     */
    public static final String THEN_EMPTY_NODE_EL = "THEN(e_cn,{});";

    /**
     * 并行EL，带上空节点
     */
    public static final String WHEN_EMPTY_NODE_EL = "WHEN(e_cn,{});";

    /**
     * accessId
     */
    public static final String ACCESS_CHAIN = "A_C#{}";

    public static final String DYNAMIC_TEST_CHAIN = "D_TC";

    /**
     * 指标，场景:应用
     * appName
     */
    public static final String INDICATOR_APP_CHAIN = "IA_C#{}";

    /**
     * 指标，场景:策略集
     * policySetCode
     */
    public static final String INDICATOR_POLICY_SET_CHAIN = "IPS_C#{}";

    /**
     * 指标路由chainId
     */
    public static final String INDICATOR_ROUTE_CHAIN = "I_RC";

    /**
     * appName,policySetCode
     */
    public static final String INDICATOR_ROUTE_CHAIN_EL = "WHEN(IA_C#{},IPS_C#{});";

    /**
     * indicatorId
     */
    public static final String INDICATOR_CHAIN = "I_C#{}";

    /**
     * 策略集路由chainId
     */
    public static final String POLICY_SET_ROUTE_CHAIN = "PS_RC";

    /**
     * policySetId
     */
    public static final String POLICY_SET_CHAIN = "PS_C#{}";

    /**
     * policyId
     */
    public static final String POLICY_CHAIN = "P_C#{}";

    /**
     * ruleId
     */
    public static final String RULE_CHAIN = "R_C#{}";

    /**
     * empty普通组件
     */
    public static final String EMPTY_COMMON_NODE = "e_cn";

    /**
     * access输入普通组件
     */
    public static final String ACCESS_IN_COMMON_NODE = "a_icn";

    /**
     * access输出普通组件
     */
    public static final String ACCESS_OUT_COMMON_NODE = "a_ocn";

    /**
     * 普通字段组件
     */
    public static final String NORMAL_FIELD_COMMON_NODE = "nf_cn";

    /**
     * 动态字段组件
     */
    public static final String DYNAMIC_FIELD_COMMON_NODE = "df_cn";

    /**
     * 指标路由普通组件
     */
    public static final String INDICATOR_ROUTE_COMMON_NODE = "i_rcn";

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
     * 策略集普通组件
     */
    public static final String POLICY_SET_COMMON_NODE = "ps_cn";

    /**
     * 策略普通组件
     */
    public static final String POLICY_COMMON_NODE = "p_cn";

    /**
     * 条件普通组件
     */
    public static final String CONDITION_COMMON_NODE = "c_cn";

    /**
     * 规则普通组件
     */
    public static final String RULE_COMMON_NODE = "r_cn";

    /**
     * 规则true普通组件
     */
    public static final String RULE_TRUE_COMMON_NODE = "r_tcn";

    /**
     * 规则false普通组件
     */
    public static final String RULE_FALSE_COMMON_NODE = "r_fcn";

    public static final String NODE_WITH_TAG = "{}.tag\"{}\"";

    /**
     * @param nodeId 节点id
     * @param tag    标签
     * @return 带标签的节点
     */
    public static String getNodeWithTag(String nodeId, Object tag) {
        return StrUtil.format(NODE_WITH_TAG, nodeId, tag.toString());
    }

    /**
     * @param oEl 原el
     * @param el  el
     * @return 新el
     */
    public static String elAdd(String oEl, String el) {
        StringBuilder sb = new StringBuilder(oEl);
        sb.insert(sb.lastIndexOf(")"), ", " + el);
        return sb.toString();
    }

    /**
     * @param oEl 原el
     * @param el  要移除的el
     * @return 去除el后的el
     */
    public static String removeEl(String oEl, String el) {
        return oEl.replace(el, "");
    }
}
