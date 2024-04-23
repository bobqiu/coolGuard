package cn.wnhyang.coolGuard.controller;

import cn.wnhyang.coolGuard.AD;
import cn.wnhyang.coolGuard.service.AnalysisService;
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

    private final AnalysisService analysisService;

    @GetMapping("getCounty")
    public AD getCity(@RequestParam("idCard") String idCard) {
        return analysisService.getCounty(idCard);
    }
}
