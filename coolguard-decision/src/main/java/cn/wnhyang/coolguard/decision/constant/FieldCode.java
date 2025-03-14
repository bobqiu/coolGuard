package cn.wnhyang.coolguard.decision.constant;

/**
 * 内置字段，系统初始化sql中带的，不可修改删除
 *
 * @author wnhyang
 * @date 2024/4/30
 **/
public interface FieldCode {

    String APP_NAME = "N_S_appName";
    String POLICY_SET_CODE = "N_S_policySetCode";

    String SEQ_ID = "N_S_seqId";

    String EVENT_TIME = "N_D_eventTime";
    String EVENT_TIME_STAMP = "N_D_eventTimeStamp";

    String PAYER_ID_NUMBER = "N_S_payerIDNumber";

    /**
     * 证件号 衍生字段
     */
    String ID_CARD_PROVINCE = "N_S_idCardProvince";
    String ID_CARD_CITY = "N_S_idCardCity";
    String ID_CARD_DISTRICT = "N_S_idCardDistrict";

    String PAYER_PHONE_NUMBER = "N_S_payerPhoneNumber";
    /**
     * 手机号 衍生字段
     */
    String PHONE_NUMBER_PROVINCE = "N_S_phoneNumberProvince";
    String PHONE_NUMBER_CITY = "N_S_phoneNumberCity";
    String PHONE_NUMBER_ISP = "N_S_phoneNumberIsp";

    String IP = "N_S_ip";

    /**
     * ip 衍生字段
     */
    String IP_COUNTRY = "N_S_ipCountry";
    String IP_PROVINCE = "N_S_ipProvince";
    String IP_CITY = "N_S_ipCity";
    String IP_ISP = "N_S_ipIsp";

    String LON_AND_LAT = "N_S_lonAndLat";

    /**
     * geo衍生字段
     */
    String GEO_PROVINCE = "N_S_geoProvince";
    String GEO_CITY = "N_S_geoCity";
    String GEO_DISTRICT = "N_S_geoDistrict";
    String GEO_HASH = "N_S_geoHash";

}
