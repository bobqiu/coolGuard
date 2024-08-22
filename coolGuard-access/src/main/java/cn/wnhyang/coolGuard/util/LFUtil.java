package cn.wnhyang.coolGuard.util;

import cn.hutool.core.util.StrUtil;
import cn.wnhyang.coolGuard.vo.Cond;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

import static cn.wnhyang.coolGuard.util.JsonUtils.buildJavaTimeModule;

/**
 * @author wnhyang
 * @date 2024/7/18
 **/
public class LFUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.registerModules(buildJavaTimeModule());
    }

    /**
     * if
     */
    public static final String IF_EL = "IF({},{},{});";

    /**
     * 并行EL，带上空节点
     */
    public static final String WHEN_EMPTY_NODE = "WHEN(e_cn);";

    /**
     * accessId
     */
    public static final String ACCESS_CHAIN = "A_C#{}";

    /**
     * 动态字段测试
     */
    public static final String DYNAMIC_TEST_CHAIN = "D_TC";

    /**
     * indicatorId
     */
    public static final String INDICATOR_CHAIN = "I_C#{}";

    /**
     * policySetId
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
    public static final String POLICY_SET_ROUTE_COMMON_NODE = "ps_rcn";

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

    public static final String NODE_WITH_TAG = "{}.tag(\"{}\")";

    /**
     * @param nodeId 节点id
     * @param tag    标签
     * @return 带标签的节点
     */
    public static String getNodeWithTag(String nodeId, Object tag) {
        return StrUtil.format(NODE_WITH_TAG, nodeId, tag.toString());
    }

    /**
     * 针对THEN和WHEN编排
     *
     * @param oEl 原el
     * @param el  el
     * @return 新el
     */
    public static String elAdd(String oEl, String el) {
        StringBuilder sb = new StringBuilder(oEl);
        sb.insert(sb.lastIndexOf(")"), "," + el);
        return sb.toString();
    }

    /**
     * @param oEl 原el
     * @param el  要移除的el
     * @return 去除el后的el
     */
    public static String removeEl(String oEl, String el) {
        return oEl.replace("," + el, "");
    }

    private static String[] splitExpressions(String expression) {
        List<String> parts = new ArrayList<>();
        int level = 0;
        int startIndex = 0;

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if (c == '(') {
                level++;
            } else if (c == ')') {
                level--;
            } else if (c == ',' && level == 0) {
                parts.add(expression.substring(startIndex, i));
                startIndex = i + 1;
            }
        }
        parts.add(expression.substring(startIndex));

        return parts.toArray(new String[0]);
    }

    @SneakyThrows
    public static String buildCondEl(Cond cond) {
        if (cond != null) {
            if (cond.getLogicOp() != null && cond.getChildren() != null && !cond.getChildren().isEmpty()) {
                List<String> expressions = cond.getChildren().stream()
                        .map(LFUtil::buildCondEl)
                        .collect(Collectors.toList());
                return cond.getLogicOp() + "(" + String.join(", ", expressions) + ")";
            } else {
                return "c_cn.data('" + objectMapper.writeValueAsString(cond) + "')";
            }
        }
        return "";
    }

    @SneakyThrows
    public static Cond parseToCond(String expression) {
        expression = expression.replaceAll("\\s+", "");
        return parseExpressionToCond(expression);
    }

    private static Cond parseExpressionToCond(String expression) throws Exception {
        if (expression.startsWith("AND(")) {
            return parseLogicExpression("AND", expression.substring(4, expression.length() - 1));
        } else if (expression.startsWith("OR(")) {
            return parseLogicExpression("OR", expression.substring(3, expression.length() - 1));
        } else if (expression.startsWith("NOT(")) {
            return parseLogicExpression("NOT", expression.substring(4, expression.length() - 1));
        } else {
            return parseVariable(expression);
        }
    }

    private static Cond parseVariable(String variableExpression) throws Exception {
        if (variableExpression.contains(".data('")) {
            int dataIndex = variableExpression.indexOf(".data('");
            String jsonData = variableExpression.substring(dataIndex + 7, variableExpression.length() - 2).trim();
            return objectMapper.readValue(jsonData, Cond.class);
        }
        return new Cond();
    }

    private static Cond parseLogicExpression(String operator, String subExpression) throws Exception {
        Cond cond = new Cond();
        cond.setLogicOp(operator);
        cond.setChildren(new ArrayList<>());
        String[] subExpressions = splitExpressions(subExpression);
        for (String subExp : subExpressions) {
            cond.getChildren().add(parseExpressionToCond(subExp.trim()));
        }
        return cond;
    }


    public static List<String> parseIfEl(String el) {
        el = el.replaceAll("\\s+", "");
        el = el.substring(3, el.length() - 2);
        List<String> params = new ArrayList<>();
        Stack<Character> stack = new Stack<>();
        StringBuilder currentParam = new StringBuilder();
        boolean inQuotes = false;

        for (char c : el.toCharArray()) {
            if (c == '(' || c == '[' || c == '{') {
                stack.push(c);
            } else if (c == ')' || c == ']' || c == '}') {
                if (!stack.isEmpty() && stack.peek() == getMatchingOpeningBracket(c)) {
                    stack.pop();
                }
            } else if (c == ',' && stack.isEmpty() && !inQuotes) {
                params.add(currentParam.toString().trim());
                currentParam.setLength(0);
                continue;
            } else if (c == '"' || c == '\'') {
                inQuotes = !inQuotes;
            }
            currentParam.append(c);
        }

        if (!currentParam.isEmpty()) {
            params.add(currentParam.toString().trim());
        }

        return params;
    }

    private static char getMatchingOpeningBracket(char closingBracket) {
        return switch (closingBracket) {
            case ')' -> '(';
            case ']' -> '[';
            case '}' -> '{';
            default -> '\0';
        };
    }

    public static void main(String[] args) throws Exception {

        Cond cond = new Cond();
        cond.setLogicOp("AND");
        List<Cond> children = new ArrayList<>();
        children.add(new Cond().setType("normal").setValue("N_S_appName").setLogicType("eq").setExpectType("input").setExpectValue("Phone"));
        children.add(new Cond().setType("normal").setValue("N_F_transAmount").setLogicType("lt").setExpectType("input").setExpectValue("100"));
        cond.setChildren(children);

        String condEl = buildCondEl(cond);
        System.out.println(condEl);

        System.out.println(parseToCond(condEl));

        String content = "IF(OR(c_cn.data('{\"type\":\"normal\",\"value\":\"N_S_appName\",\"logicType\":\"eq\",\"expectType\":\"input\",\"expectValue\":\"Phone\"}'),c_cn.data('{\"type\":\"normal\",\"value\":\"N_S_payerAccount\",\"logicType\":\"eq\",\"expectType\":\"input\",\"expectValue\":\"123456\"}'),c_cn.data('{\"type\":\"normal\",\"value\":\"N_F_transAmount\",\"logicType\":\"gt\",\"expectType\":\"input\",\"expectValue\":\"15\"}')),r_tcn.tag(\"1\"),r_fcn);";

        List<String> ifEl = parseIfEl(content);
        for (String parseParam : ifEl) {
            System.out.println(parseParam);
        }

        Cond parseToCond = parseToCond(ifEl.get(0));
        System.out.println(parseToCond);


    }

}
