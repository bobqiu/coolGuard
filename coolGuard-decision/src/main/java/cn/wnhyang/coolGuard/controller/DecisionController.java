package cn.wnhyang.coolGuard.controller;

import cn.wnhyang.coolGuard.context.DecisionResponse;
import cn.wnhyang.coolGuard.pojo.CommonResult;
import cn.wnhyang.coolGuard.service.DecisionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static cn.wnhyang.coolGuard.pojo.CommonResult.success;

/**
 * 决策
 *
 * @author wnhyang
 * @date 2024/3/13
 **/
@RestController
@RequestMapping("/decision")
@RequiredArgsConstructor
@Slf4j
public class DecisionController {

    private final DecisionService decisionService;

    /**
     * 同步风险
     *
     * @param name   服务名
     * @param params 参数
     * @return map
     */
    @PostMapping("/{name}/sync")
    public CommonResult<DecisionResponse> syncRisk(@PathVariable("name") String name, @RequestBody Map<String, String> params) {
        return success(decisionService.syncRisk(name, params));
    }

    /**
     * 异步风险
     *
     * @param name   服务名
     * @param params 参数
     * @return map
     */
    @PostMapping("/{name}/async")
    public CommonResult<DecisionResponse> asyncRisk(@PathVariable("name") String name, @RequestBody Map<String, String> params) {
        return success(decisionService.asyncRisk(name, params));
    }


}
