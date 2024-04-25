package cn.wnhyang.coolGuard.controller;

import cn.wnhyang.coolGuard.entity.ad.Pca;
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
 * @date 2024/4/25
 **/
@Slf4j
@RestController
@RequestMapping("/adoc")
@RequiredArgsConstructor
public class AdocController {

    @PostConstruct
    public void init() {
        AdocUtil.init();
    }

    @GetMapping
    public Pca get(@RequestParam("code") String code) {
        Pca pca = AdocUtil.getPca(code);
        return pca;
    }
}
