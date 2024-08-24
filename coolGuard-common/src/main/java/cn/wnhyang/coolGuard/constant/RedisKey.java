package cn.wnhyang.coolGuard.constant;

/**
 * @author wnhyang
 * @date 2024/3/13
 **/
public interface RedisKey {

    String LOCK = "lock";

    /**
     * 指标
     */
    String ZB = "zb:";

    /**
     * 应用
     */
    String APPLICATION = "app";

    /**
     * 链路
     */
    String CHAIN = "chain";

    /**
     * 处置
     */
    String DISPOSAL = "disposal";

    /**
     * 字段
     */
    String FIELD = "field";

    /**
     * 字段分组
     */
    String FIELD_GROUP = "fieldGroup";

    /**
     * 指标
     */
    String INDICATOR = "indicator";

    /**
     * 指标s
     */
    String INDICATORS = "indicators";

    /**
     * 规则
     */
    String RULE = "rule";

    /**
     * 规则s
     */
    String RULES = "rules";

    String ACCESS = "access";

    String POLICY = "policy";

    String POLICY_SET = "policy_set";

    String LIST_DATA = "list_data";

    String VALUES = "values";

}
