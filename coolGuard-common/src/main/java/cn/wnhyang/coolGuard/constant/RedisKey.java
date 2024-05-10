package cn.wnhyang.coolGuard.constant;

/**
 * @author wnhyang
 * @date 2024/3/13
 **/
public interface RedisKey {

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
     * 规则
     */
    String RULE = "rule";

    /**
     * 规则s
     */
    String RULES = "rules";

    String ACCESS = "access";

    String STRATEGY = "strategy";

    String STRATEGY_SET = "strategy_set";

}
