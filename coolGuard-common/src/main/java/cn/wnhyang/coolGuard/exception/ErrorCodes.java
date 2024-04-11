package cn.wnhyang.coolGuard.exception;

/**
 * @author wnhyang
 * @date 2024/4/6
 **/
public interface ErrorCodes {

    ErrorCode APPLICATION_NOT_EXIST = new ErrorCode(1001001000, "应用不存在");

    ErrorCode APPLICATION_NAME_EXIST = new ErrorCode(1001001001, "应用名已存在");

    ErrorCode DISPOSAL_NOT_EXIST = new ErrorCode(1002001000, "处置不存在");

    ErrorCode DISPOSAL_CODE_EXIST = new ErrorCode(1002001001, "处置代码已存在");

    ErrorCode SERVICE_CONFIG_NOT_EXIST = new ErrorCode(1003001000, "服务配置不存在");

    ErrorCode SERVICE_CONFIG_NAME_EXIST = new ErrorCode(1003001001, "服务配置名已存在");

    ErrorCode FIELD_NOT_EXIST = new ErrorCode(1004001000, "字段不存在");

    ErrorCode FIELD_NAME_EXIST = new ErrorCode(1004001001, "字段名已存在");

    ErrorCode FIELD_GROUP_NOT_EXIST = new ErrorCode(1004001002, "字段组不存在");

    ErrorCode FIELD_GROUP_NAME_EXIST = new ErrorCode(1004001003, "字段组名已存在");

    ErrorCode RULE_NOT_EXIST = new ErrorCode(1005001000, "规则不存在");

    ErrorCode RULE_CODE_EXIST = new ErrorCode(1005001001, "规则code已存在");

    ErrorCode STRATEGY_NOT_EXIST = new ErrorCode(1006001000, "策略不存在");

    ErrorCode STRATEGY_CODE_EXIST = new ErrorCode(1006001001, "策略code已存在");

    ErrorCode STRATEGY_SET_NOT_EXIST = new ErrorCode(1006001002, "策略集不存在");

    ErrorCode STRATEGY_SET_CODE_EXIST = new ErrorCode(1006001003, "策略集code已存在");

}
