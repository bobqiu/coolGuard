package cn.wnhyang.coolguard.decision.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaIgnore;
import cn.wnhyang.coolguard.common.pojo.CommonResult;
import cn.wnhyang.coolguard.decision.service.DecisionService;
import cn.wnhyang.coolguard.decision.vo.result.DecisionResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static cn.wnhyang.coolguard.common.pojo.CommonResult.success;

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
    @SaCheckLogin
    public CommonResult<DecisionResult> testRisk(@PathVariable("code") String code, @RequestBody Map<String, String> params) {
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
    @SaIgnore
    public CommonResult<DecisionResult> syncRisk(@PathVariable("code") String code, @RequestBody Map<String, String> params) {
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
    @SaIgnore
    public CommonResult<DecisionResult> asyncRisk(@PathVariable("code") String code, @RequestBody Map<String, String> params) {
        return success(decisionService.asyncRisk(code, params));
    }
}
