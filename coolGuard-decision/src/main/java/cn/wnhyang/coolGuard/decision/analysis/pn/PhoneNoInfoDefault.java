package cn.wnhyang.coolGuard.decision.analysis.pn;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wnhyang
 * @date 2024/5/31
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneNoInfoDefault implements PhoneNoInfo {

    /**
     * 手机号
     */
    private String phoneNumber;

    /**
     * 国家
     */
    private String country;

    /**
     * 地区
     */
    private String area;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * ISP
     */
    private String isp;

    public static PhoneNoInfoDefault unknown(String phoneNumber) {
        return new PhoneNoInfoDefault(phoneNumber, UNKNOWN, UNKNOWN, UNKNOWN, UNKNOWN, UNKNOWN);
    }
}
