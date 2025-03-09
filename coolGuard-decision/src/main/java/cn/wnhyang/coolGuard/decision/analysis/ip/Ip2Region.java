package cn.wnhyang.coolGuard.decision.analysis.ip;

/**
 * 国家|区域|省份|城市|ISP
 *
 * @author wnhyang
 * @date 2024/4/30
 **/
public interface Ip2Region {

    String UNKNOWN = "未知";

    /**
     * 获取IP地址
     *
     * @return IP地址
     */
    String getIp();

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
