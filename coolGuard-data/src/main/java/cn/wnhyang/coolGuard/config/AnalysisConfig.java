package cn.wnhyang.coolGuard.config;

import cn.wnhyang.coolGuard.analysis.geo.GeoAnalysis;
import cn.wnhyang.coolGuard.analysis.geo.GeoAnalysisDefault;
import cn.wnhyang.coolGuard.analysis.ip.IpAnalysis;
import cn.wnhyang.coolGuard.analysis.ip.IpAnalysisDefaultImpl;
import cn.wnhyang.coolGuard.analysis.pn.PhoneNoAnalysis;
import cn.wnhyang.coolGuard.analysis.pn.PhoneNoAnalysisDefault;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wnhyang
 * @date 2024/5/31
 **/
@Configuration
public class AnalysisConfig {

    @Bean
    @ConditionalOnMissingBean
    public IpAnalysis ipAnalysis() {
        IpAnalysis ipAnalysis = new IpAnalysisDefaultImpl();
        ipAnalysis.init();
        return ipAnalysis;
    }

    @Bean
    @ConditionalOnMissingBean
    public PhoneNoAnalysis phoneNoAnalysis() {
        PhoneNoAnalysis phoneNoAnalysis = new PhoneNoAnalysisDefault();
        phoneNoAnalysis.init();
        return phoneNoAnalysis;
    }

    @Bean
    @ConditionalOnMissingBean
    public GeoAnalysis geoAnalysis() {
        GeoAnalysis geoAnalysis = new GeoAnalysisDefault();
        geoAnalysis.init();
        return geoAnalysis;
    }
}
