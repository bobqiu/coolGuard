package cn.wnhyang.coolGuard.controller;

import cn.wnhyang.coolGuard.pojo.CommonResult;
import cn.wnhyang.coolGuard.service.DecisionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static cn.wnhyang.coolGuard.pojo.CommonResult.success;

/**
 * 决策
 *
 * @author wnhyang
 * @date 2025/3/8
 **/
@RestController
@RequestMapping("/decision")
@RequiredArgsConstructor
public class DecisionController {

    private final DecisionService decisionService;

    /**
     * 测试接入
     *
     * @param code   服务名
     * @param params 参数
     * @return map
     */
    @PostMapping("/{code}/test")
    public CommonResult<Map<String, Object>> testRisk(@PathVariable("code") String code, @RequestBody Map<String, String> params) {
        return success(decisionService.testRisk(code, params));
    }

    /**
     * 同步接入
     *
     * @param code   服务code
     * @param params 参数
     * @return map
     */
    @PostMapping("/{code}/sync")
    public CommonResult<Map<String, Object>> syncRisk(@PathVariable("code") String code, @RequestBody Map<String, String> params) {
        return success(decisionService.syncRisk(code, params));
    }

    /**
     * 异步接入
     *
     * @param code   服务code
     * @param params 参数
     * @return map
     */
    @PostMapping("/{code}/async")
    public CommonResult<Map<String, Object>> asyncRisk(@PathVariable("code") String code, @RequestBody Map<String, String> params) {
        return success(decisionService.asyncRisk(code, params));
    }
}
