package cn.wnhyang.coolGuard.decision.config;

import cn.wnhyang.coolGuard.decision.analysis.geo.GeoAnalysis;
import cn.wnhyang.coolGuard.decision.analysis.geo.GeoAnalysisDefault;
import cn.wnhyang.coolGuard.decision.analysis.idcard.IdCardAnalysis;
import cn.wnhyang.coolGuard.decision.analysis.idcard.IdCardAnalysisDefaultImpl;
import cn.wnhyang.coolGuard.decision.analysis.ip.IpAnalysis;
import cn.wnhyang.coolGuard.decision.analysis.ip.IpAnalysisDefaultImpl;
import cn.wnhyang.coolGuard.decision.analysis.pn.PhoneNoAnalysis;
import cn.wnhyang.coolGuard.decision.analysis.pn.PhoneNoAnalysisDefault;
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
    public IdCardAnalysis idCardAnalysis() {
        IdCardAnalysis idCardAnalysis = new IdCardAnalysisDefaultImpl();
        idCardAnalysis.init();
        return idCardAnalysis;
    }

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
