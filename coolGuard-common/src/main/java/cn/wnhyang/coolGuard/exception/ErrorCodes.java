package cn.wnhyang.coolGuard.exception;

/**
 * @author wnhyang
 * @date 2024/4/6
 **/
public interface ErrorCodes {

    ErrorCode APPLICATION_NOT_EXIST = new ErrorCode(1001001000, "应用不存在");

    ErrorCode APPLICATION_NAME_EXIST = new ErrorCode(1001001001, "应用名已存在");

    ErrorCode APPLICATION_REFERENCE_DELETE = new ErrorCode(1006001002, "应用有引用不可删除");

    ErrorCode DISPOSAL_NOT_EXIST = new ErrorCode(1002001000, "处置不存在");

    ErrorCode DISPOSAL_CODE_EXIST = new ErrorCode(1002001001, "处置代码已存在");

    ErrorCode DISPOSAL_STANDARD = new ErrorCode(1002001002, "标准处置不可更改");

    ErrorCode DISPOSAL_REFERENCE = new ErrorCode(1002001003, "处置有引用不可删除");

    ErrorCode ACCESS_NOT_EXIST = new ErrorCode(1003001000, "接入不存在");

    ErrorCode ACCESS_NAME_EXIST = new ErrorCode(1003001001, "接入名已存在");

    ErrorCode FIELD_NOT_EXIST = new ErrorCode(1004001000, "字段不存在");

    ErrorCode FIELD_NAME_EXIST = new ErrorCode(1004001001, "字段名已存在");

    ErrorCode FIELD_STANDARD = new ErrorCode(1004001002, "标准字段名不可更改");

    ErrorCode FIELD_GROUP_NOT_EXIST = new ErrorCode(1004001002, "字段组不存在");

    ErrorCode FIELD_GROUP_NAME_EXIST = new ErrorCode(1004001003, "字段组名已存在");

    ErrorCode FIELD_GROUP_HAS_FIELD = new ErrorCode(1004001004, "字段组存在字段");

    ErrorCode FIELD_GROUP_STANDARD = new ErrorCode(1004001005, "标准字段组不可更改");

    ErrorCode RULE_NOT_EXIST = new ErrorCode(1005001000, "规则不存在");

    ErrorCode RULE_CODE_EXIST = new ErrorCode(1005001001, "规则code已存在");

    ErrorCode POLICY_NOT_EXIST = new ErrorCode(1006001000, "策略不存在");

    ErrorCode POLICY_CODE_EXIST = new ErrorCode(1006001001, "策略code已存在");

    ErrorCode POLICY_REFERENCE_DELETE = new ErrorCode(1006001002, "策略有引用不可删除");

    ErrorCode POLICY_REFERENCE_UPDATE = new ErrorCode(1006001003, "策略下有运行规则，不可关闭当前策略");

    ErrorCode POLICY_SET_NOT_EXIST = new ErrorCode(1007001000, "策略集不存在");

    ErrorCode POLICY_SET_CODE_EXIST = new ErrorCode(1007001001, "策略集code已存在");

    ErrorCode POLICY_SET_REFERENCE_DELETE = new ErrorCode(1007001002, "策略集有引用不可删除");

    ErrorCode POLICY_SET_REFERENCE_UPDATE = new ErrorCode(1007001003, "策略集下有运行策略，不可关闭当前策略集");

    ErrorCode INDICATOR_IS_RUNNING = new ErrorCode(1008001000, "指标正在运行中，不可删除");

    ErrorCode INDICATOR_NOT_EXIST = new ErrorCode(1008001001, "指标不存在");

    ErrorCode INDICATOR_CODE_EXIST = new ErrorCode(1008001002, "指标code已存在");

    ErrorCode INDICATOR_REFERENCE_DELETE = new ErrorCode(1008001003, "指标有引用不可删除");

    ErrorCode INDICATOR_VERSION_EXIST = new ErrorCode(1008001004, "指标版本已存在");


}
