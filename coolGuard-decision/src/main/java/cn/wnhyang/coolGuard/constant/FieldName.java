package cn.wnhyang.coolGuard.constant;

/**
 * 内置字段，系统初始化sql中带的，不可修改删除
 *
 * @author wnhyang
 * @date 2024/4/30
 **/
public interface FieldName {

    String appName = "N_S_appName";
    String policySetCode = "N_S_policySetCode";

    String seqId = "N_S_seqId";

    String eventTime = "N_D_eventTime";
    String eventTimeStamp = "N_D_eventTimeStamp";

    String payerIDNumber = "N_S_payerIDNumber";

    /**
     * 证件号 衍生字段
     */
    String idCardProvince = "N_S_idCardProvince";
    String idCardCity = "N_S_idCardCity";
    String idCardDistrict = "N_S_idCardDistrict";

    String payerPhoneNumber = "N_S_payerPhoneNumber";
    /**
     * 手机号 衍生字段
     */
    String phoneNumberProvince = "N_S_phoneNumberProvince";
    String phoneNumberCity = "N_S_phoneNumberCity";
    String phoneNumberIsp = "N_S_phoneNumberIsp";

    String ip = "N_S_ip";

    /**
     * ip 衍生字段
     */
    String ipCountry = "N_S_ipCountry";
    String ipProvince = "N_S_ipProvince";
    String ipCity = "N_S_ipCity";
    String ipIsp = "N_S_ipIsp";

    String lonAndLat = "N_S_lonAndLat";

    /**
     * geo衍生字段
     */
    String geoProvince = "N_S_geoProvince";
    String geoCity = "N_S_geoCity";
    String geoDistrict = "N_S_geoDistrict";
    String geoHash = "N_S_geoHash";

}
