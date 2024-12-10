package cn.wnhyang.coolGuard.analysis.pn;

/**
 * @author wnhyang
 * @date 2024/5/31
 **/
public interface PhoneNoInfo {

    String UNKNOWN = "未知";

    String CN = "中国";

    /**
     * 获取手机号
     *
     * @return 手机号
     */
    String getPhoneNumber();

    /**
     * 获取国家
     *
     * @return 国家
     */
    String getCountry();

    /**
     * 获取地区
     *
     * @return 地区
     */
    String getArea();

    /**
     * 获取省份
     *
     * @return 省份
     */
    String getProvince();

    /**
     * 获取城市
     *
     * @return 城市
     */
    String getCity();

    /**
     * 获取ISP
     *
     * @return ISP
     */
    String getIsp();
}
