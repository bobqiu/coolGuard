package cn.wnhyang.coolGuard.controller;

import cn.wnhyang.coolGuard.service.CondService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 条件
 *
 * @author wnhyang
 * @date 2024/7/29
 **/
@Slf4j
@RestController
@RequestMapping("/cond")
@RequiredArgsConstructor
public class CondController {

    private final CondService condService;


}
