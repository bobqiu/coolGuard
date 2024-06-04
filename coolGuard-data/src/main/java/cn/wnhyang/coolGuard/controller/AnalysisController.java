package cn.wnhyang.coolGuard.controller;

import cn.wnhyang.coolGuard.analysis.GeoAnalysis;
import cn.wnhyang.coolGuard.analysis.IpAnalysis;
import cn.wnhyang.coolGuard.analysis.PhoneNoAnalysis;
import cn.wnhyang.coolGuard.analysis.ad.Pca;
import cn.wnhyang.coolGuard.analysis.ip.Ip2Region;
import cn.wnhyang.coolGuard.analysis.pn.PhoneNoInfo;
import cn.wnhyang.coolGuard.util.AdocUtil;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wnhyang
 * @date 2024/4/19
 **/
@Slf4j
@RestController
@RequestMapping("/analysis")
@RequiredArgsConstructor
public class AnalysisController {

    @PostConstruct
    public void init() {
        AdocUtil.init();
    }

    private final PhoneNoAnalysis phoneNoAnalysis;

    private final IpAnalysis ipAnalysis;

    private final GeoAnalysis geoAnalysis;

    @GetMapping("/pca")
    public Pca getPca(@RequestParam("code") String code) {
        Pca pca = AdocUtil.getPca(code);
        return pca;
    }

    @GetMapping("/phone")
    public PhoneNoInfo getPhone(@RequestParam("phone") String phone) {
        return phoneNoAnalysis.analysis(phone);
    }

    @GetMapping("/ip")
    public Ip2Region getIp(@RequestParam("ip") String ip) {
        return ipAnalysis.analysis(ip);
    }

    @GetMapping("/geo")
    public Pca getGeo(@RequestParam("lonAndLat") String lonAndLat) {
        return geoAnalysis.analysis(lonAndLat);
    }

}
