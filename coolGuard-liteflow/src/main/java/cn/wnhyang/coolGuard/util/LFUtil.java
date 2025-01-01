package cn.wnhyang.coolGuard.util;

import cn.hutool.core.util.StrUtil;
import cn.wnhyang.coolGuard.entity.Cond;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

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
        return StrUtil.format("{}.data(\"{}\")", nodeId, data);
    }

    public static String buildWhen(String... el) {
        return "when(" + StrUtil.join(",", el) + ");";
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
            if (cond.getRelation() != null && cond.getChildren() != null && !cond.getChildren().isEmpty()) {
                List<String> expressions = cond.getChildren().stream()
                        .map(LFUtil::buildCondEl)
                        .collect(Collectors.toList());
                return cond.getRelation() + "(" + String.join(", ", expressions) + ")";
            } else {
                return "c_cn.data('" + JsonUtil.toJsonString(cond) + "')";
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
        if (expression.startsWith("and(")) {
            return parseLogicExpression("and", expression.substring(4, expression.length() - 1));
        } else if (expression.startsWith("or(")) {
            return parseLogicExpression("or", expression.substring(3, expression.length() - 1));
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
            return JsonUtil.parseObject(jsonData, Cond.class);
        }
        return new Cond();
    }

    private static Cond parseLogicExpression(String operator, String subExpression) throws Exception {
        Cond cond = new Cond();
        cond.setRelation(operator);
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
        cond.setRelation("and");
        List<Cond> children = new ArrayList<>();
        children.add(new Cond().setType("normal").setLeftValue("N_S_appName").setLogicType("eq").setRightType("input").setRightValue("Phone"));
        children.add(new Cond().setType("normal").setLeftValue("N_F_transAmount").setLogicType("lt").setRightType("input").setRightValue("100"));
        cond.setChildren(children);

        String condEl = buildCondEl(cond);
        System.out.println(condEl);

        System.out.println(parseToCond(condEl));

        String content = "IF(or(c_cn.data('{\"type\":\"normal\",\"value\":\"N_S_appName\",\"logicType\":\"eq\",\"expectType\":\"input\",\"expectValue\":\"Phone\"}'),c_cn.data('{\"type\":\"normal\",\"value\":\"N_S_payerAccount\",\"logicType\":\"eq\",\"expectType\":\"input\",\"expectValue\":\"123456\"}'),c_cn.data('{\"type\":\"normal\",\"value\":\"N_F_transAmount\",\"logicType\":\"gt\",\"expectType\":\"input\",\"expectValue\":\"15\"}')),r_tcn.tag(\"1\"),r_fcn);";

        List<String> ifEl = parseIfEl(content);
        for (String parseParam : ifEl) {
            System.out.println(parseParam);
        }

        Cond parseToCond = parseToCond(ifEl.get(0));
        System.out.println(parseToCond);


        System.out.println(buildWhen("3244", "323r", "ergre", "regreg"));
    }
}
