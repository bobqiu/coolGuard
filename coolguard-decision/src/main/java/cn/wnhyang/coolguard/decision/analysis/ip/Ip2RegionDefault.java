package cn.wnhyang.coolguard.decision.analysis.ip;

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
public class Ip2RegionDefault implements Ip2Region {

    /**
     * ip
     */
    private String ip;

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

    public static Ip2RegionDefault unknown(String ip) {
        return new Ip2RegionDefault(ip, UNKNOWN, UNKNOWN, UNKNOWN, UNKNOWN, UNKNOWN);
    }
}
