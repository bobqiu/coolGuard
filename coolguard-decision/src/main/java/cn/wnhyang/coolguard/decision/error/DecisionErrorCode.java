package cn.wnhyang.coolguard.decision.error;

import cn.wnhyang.coolguard.common.exception.ErrorCode;

/**
 * @author wnhyang
 * @date 2024/4/6
 **/
public interface DecisionErrorCode {

    ErrorCode APPLICATION_NOT_EXIST = new ErrorCode(1001001000, "应用不存在");

    ErrorCode APPLICATION_CODE_EXIST = new ErrorCode(1001001001, "应用编码已存在");

    ErrorCode APPLICATION_NAME_EXIST = new ErrorCode(1001001001, "应用名已存在");

    ErrorCode APPLICATION_REFERENCE_DELETE = new ErrorCode(1006001002, "应用有引用不可删除");

    ErrorCode DISPOSAL_NOT_EXIST = new ErrorCode(1002001000, "处置不存在");

    ErrorCode DISPOSAL_CODE_EXIST = new ErrorCode(1002001001, "处置编码已存在");

    ErrorCode DISPOSAL_NAME_EXIST = new ErrorCode(1002001002, "处置名已存在");

    ErrorCode DISPOSAL_STANDARD = new ErrorCode(1002001003, "标准处置不可更改");

    ErrorCode DISPOSAL_REFERENCE = new ErrorCode(1002001004, "处置有引用不可删除");

    ErrorCode ACCESS_NOT_EXIST = new ErrorCode(1003001000, "接入不存在");

    ErrorCode ACCESS_CODE_EXIST = new ErrorCode(1003001001, "接入名已存在");

    ErrorCode FIELD_NOT_EXIST = new ErrorCode(1004001000, "字段不存在");

    ErrorCode FIELD_CODE_EXIST = new ErrorCode(1004001001, "字段名已存在");

    ErrorCode FIELD_STANDARD = new ErrorCode(1004001002, "标准字段名不可更改");

    ErrorCode FIELD_CODE_ERROR = new ErrorCode(1004001003, "字段名格式错误");

    ErrorCode FIELD_GROUP_NOT_EXIST = new ErrorCode(1004001002, "字段组不存在");

    ErrorCode FIELD_GROUP_CODE_EXIST = new ErrorCode(1004001003, "字段组名已存在");

    ErrorCode FIELD_GROUP_HAS_FIELD = new ErrorCode(1004001004, "字段组存在字段");

    ErrorCode FIELD_GROUP_STANDARD = new ErrorCode(1004001005, "标准字段组不可更改");

    ErrorCode FIELD_REF_NOT_EXIST = new ErrorCode(1004001000, "字段引用不存在");

    ErrorCode FIELD_REF_EXIST = new ErrorCode(1004001001, "字段引用已存在");

    ErrorCode RULE_NOT_EXIST = new ErrorCode(1005001000, "规则不存在");

    ErrorCode RULE_CODE_EXIST = new ErrorCode(1005001001, "规则编码已存在");

    ErrorCode RULE_NAME_EXIST = new ErrorCode(1005001002, "规则名已存在");

    ErrorCode RULE_RULE_ID_EXIST = new ErrorCode(1005001003, "规则id已存在");

    ErrorCode RULE_IS_RUNNING = new ErrorCode(1005001004, "规则还在运行");

    ErrorCode RULE_VERSION_EXIST = new ErrorCode(1005001004, "规则版本已存在");

    ErrorCode POLICY_NOT_EXIST = new ErrorCode(1006001000, "策略不存在");

    ErrorCode POLICY_CODE_EXIST = new ErrorCode(1006001001, "策略编码已存在");

    ErrorCode POLICY_NAME_EXIST = new ErrorCode(1006001002, "策略名已存在");

    ErrorCode POLICY_REFERENCE_BY_POLICY_SET_DELETE = new ErrorCode(1006001003, "策略被策略集引用不可删除");

    ErrorCode POLICY_REFERENCE_RULE_DELETE = new ErrorCode(1006001004, "策略有引用不可删除");

    ErrorCode POLICY_REFERENCE_UPDATE = new ErrorCode(1006001005, "策略下有运行规则，不可关闭当前策略");

    ErrorCode POLICY_IS_RUNNING = new ErrorCode(1007001006, "策略还在运行");

    ErrorCode POLICY_VERSION_EXIST = new ErrorCode(1007001007, "策略版本已存在");

    ErrorCode POLICY_SET_NOT_EXIST = new ErrorCode(1007001000, "策略集不存在");

    ErrorCode POLICY_SET_CODE_EXIST = new ErrorCode(1007001001, "策略集编码已存在");

    ErrorCode POLICY_SET_NAME_EXIST = new ErrorCode(1007001002, "策略集名已存在");

    ErrorCode POLICY_SET_REFERENCE_DELETE = new ErrorCode(1007001003, "策略集有引用不可删除");

    ErrorCode POLICY_SET_REFERENCE_UPDATE = new ErrorCode(1007001004, "策略集下有运行策略，不可关闭当前策略集");

    ErrorCode POLICY_SET_CHAIN_NOT_CHANGE = new ErrorCode(1007001005, "策略集链路没有修改");

    ErrorCode POLICY_SET_IS_RUNNING = new ErrorCode(1007001006, "策略集还在运行");

    ErrorCode INDICATOR_NOT_EXIST = new ErrorCode(1008001000, "指标不存在");

    ErrorCode INDICATOR_CODE_EXIST = new ErrorCode(1008001001, "指标编码已存在");

    ErrorCode INDICATOR_NAME_EXIST = new ErrorCode(1008001002, "指标名已存在");

    ErrorCode INDICATOR_REFERENCE_DELETE = new ErrorCode(1008001003, "指标有引用不可删除");

    ErrorCode INDICATOR_VERSION_NOT_EXIST = new ErrorCode(1008001004, "指标版本不存在");

    ErrorCode INDICATOR_VERSION_EXIST = new ErrorCode(1008001005, "指标版本已存在");

    ErrorCode INDICATOR_IS_RUNNING = new ErrorCode(1008001006, "指标有运行版本，不可删除");

    ErrorCode INDICATOR_NOT_CHANGE = new ErrorCode(1008001007, "指标未修改");

    ErrorCode POLICY_SET_VERSION_NOT_EXIST = new ErrorCode(1009001001, "策略集版本不存在");

    ErrorCode POLICY_SET_VERSION_EXIST = new ErrorCode(1009001002, "策略集版本已存在");

    ErrorCode POLICY_VERSION_NOT_EXIST = new ErrorCode(1010001002, "策略版本不存在");

    ErrorCode RULE_VERSION_NOT_EXIST = new ErrorCode(1011001001, "规则版本不存在");

    ErrorCode LIST_SET_CODE_EXIST = new ErrorCode(1012001001, "名单集编号已存在");

    ErrorCode LIST_SET_NOE_EXIST = new ErrorCode(1012001002, "名单集不存在");

    ErrorCode LIST_SET_HAS_DATA = new ErrorCode(1012001003, "名单集有数据");

    ErrorCode SMS_TEMPLATE_CODE_EXIST = new ErrorCode(1013001001, "消息模版编号已存在");

    ErrorCode SMS_TEMPLATE_NOT_EXIST = new ErrorCode(1013001002, "消息模版不存在");

    ErrorCode TAG_CODE_EXIST = new ErrorCode(1014001001, "标签编号已存在");

    ErrorCode TAG_NOT_EXIST = new ErrorCode(1014001002, "标签不存在");

    ErrorCode LITE_FLOW_ERROR = new ErrorCode(1099001001, "liteflow异常{}");

}
