package cn.wnhyang.coolGuard.decision.analysis.ip;

import cn.hutool.core.util.StrUtil;
import cn.wnhyang.coolGuard.decision.constant.FieldCode;
import cn.wnhyang.coolGuard.decision.context.FieldContext;
import cn.wnhyang.coolGuard.decision.enums.FieldType;

/**
 * @author wnhyang
 * @date 2024/5/31
 **/
public interface IpAnalysis {

    default void init() {

    }

    /**
     * 获取ip2region
     *
     * @param ip ip
     * @return ip2region
     */
    Ip2Region analysis(String ip);

    default void parseIp(FieldContext fieldContext) {
        String ip = fieldContext.getData(FieldCode.IP, String.class);
        if (StrUtil.isNotBlank(ip)) {
            Ip2Region ip2Region = analysis(ip);
            if (ip2Region != null) {
                fieldContext.setDataByType(FieldCode.IP_COUNTRY, ip2Region.getCountry(), FieldType.STRING);
                fieldContext.setDataByType(FieldCode.IP_PROVINCE, ip2Region.getProvince(), FieldType.STRING);
                fieldContext.setDataByType(FieldCode.IP_CITY, ip2Region.getCity(), FieldType.STRING);
                fieldContext.setDataByType(FieldCode.IP_ISP, ip2Region.getIsp(), FieldType.STRING);
            }
        }
    }
}
