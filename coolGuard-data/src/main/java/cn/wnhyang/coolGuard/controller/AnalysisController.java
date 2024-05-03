package cn.wnhyang.coolGuard.controller;

import cn.wnhyang.coolGuard.entity.ad.Pca;
import cn.wnhyang.coolGuard.util.AdocUtil;
import cn.wnhyang.coolGuard.util.GeoUtil;
import cn.wnhyang.coolGuard.util.IpUtil;
import cn.wnhyang.coolGuard.util.PhoneNumberUtil;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.ihxq.projects.pna.PhoneNumberInfo;
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
        IpUtil.init();
        GeoUtil.init();
    }

    @GetMapping("/pca")
    public Pca getPca(@RequestParam("code") String code) {
        Pca pca = AdocUtil.getPca(code);
        return pca;
    }

    @GetMapping("/phone")
    public PhoneNumberInfo getPhone(@RequestParam("phone") String phone) {
        return PhoneNumberUtil.lookup(phone);
    }

    @GetMapping("/ip")
    public String getIp(@RequestParam("ip") String ip) {
        return IpUtil.search(ip);
    }

    @GetMapping("/geo")
    public Pca getGeo(@RequestParam("lonAndLat") String lonAndLat) {
        return GeoUtil.getPcaByGeo(lonAndLat);
    }

}
