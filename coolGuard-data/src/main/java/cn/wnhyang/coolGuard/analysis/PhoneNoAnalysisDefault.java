package cn.wnhyang.coolGuard.analysis;

import cn.wnhyang.coolGuard.analysis.pn.PhoneNoInfo;
import cn.wnhyang.coolGuard.analysis.pn.PhoneNoInfoDefault;
import lombok.extern.slf4j.Slf4j;
import me.ihxq.projects.pna.Attribution;
import me.ihxq.projects.pna.ISP;
import me.ihxq.projects.pna.PhoneNumberInfo;
import me.ihxq.projects.pna.PhoneNumberLookup;

/**
 * @author wnhyang
 * @date 2024/5/31
 **/
@Slf4j
public class PhoneNoAnalysisDefault implements PhoneNoAnalysis {

    private static PhoneNumberLookup lookup;

    @Override
    public void init() {
        lookup = new PhoneNumberLookup();
    }

    @Override
    public PhoneNoInfo analysis(String phoneNumber) {
        try {
            PhoneNumberInfo phoneNumberInfo = lookup.lookup(phoneNumber).orElseThrow();
            Attribution attribution = phoneNumberInfo.getAttribution();
            ISP isp = phoneNumberInfo.getIsp();
            return new PhoneNoInfoDefault(phoneNumber, PhoneNoInfo.CN, attribution.getProvince(), PhoneNoInfo.UNKNOWN, attribution.getCity(), isp.getCnName());
        } catch (Exception e) {
            log.error("手机号解析失败", e);
            return PhoneNoInfoDefault.unknown(phoneNumber);
        }
    }
}
