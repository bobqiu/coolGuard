package cn.wnhyang.coolGuard.controller;

import cn.wnhyang.coolGuard.entity.LabelValue;
import cn.wnhyang.coolGuard.pojo.CommonResult;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.PolicySetService;
import cn.wnhyang.coolGuard.vo.PolicySetVO;
import cn.wnhyang.coolGuard.vo.base.VersionSubmitVO;
import cn.wnhyang.coolGuard.vo.create.PolicySetCreateVO;
import cn.wnhyang.coolGuard.vo.page.PolicySetPageVO;
import cn.wnhyang.coolGuard.vo.update.PolicySetUpdateVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static cn.wnhyang.coolGuard.pojo.CommonResult.success;

/**
 * 策略集
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@RestController
@RequestMapping("/policySet")
@RequiredArgsConstructor
public class PolicySetController {

    private final PolicySetService policySetService;

    /**
     * 新增
     *
     * @param createVO 创建VO
     * @return id
     */
    @PostMapping
    public CommonResult<Long> createPolicySet(@RequestBody @Valid PolicySetCreateVO createVO) {
        return success(policySetService.createPolicySet(createVO));
    }

    /**
     * 更新
     *
     * @param updateVO 更新VO
     * @return true/false
     */
    @PutMapping
    public CommonResult<Boolean> updatePolicySet(@RequestBody @Valid PolicySetUpdateVO updateVO) {
        policySetService.updatePolicySet(updateVO);
        return success(true);
    }

    /**
     * 删除
     *
     * @param id id
     * @return true/false
     */
    @DeleteMapping
    public CommonResult<Boolean> deletePolicySet(@RequestParam("id") Long id) {
        policySetService.deletePolicySet(id);
        return success(true);
    }

    /**
     * 提交
     *
     * @param submitVO submitVO
     * @return true/false
     */
    @PostMapping("/submit")
    public CommonResult<Boolean> submit(@RequestBody @Valid VersionSubmitVO submitVO) {
        policySetService.submit(submitVO);
        return success(true);
    }

    /**
     * 查询单个
     *
     * @param id id
     * @return vo
     */
    @GetMapping
    public CommonResult<PolicySetVO> getPolicySet(@RequestParam("id") Long id) {
        return success(policySetService.getPolicySet(id));
    }

    /**
     * 分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    @GetMapping("/page")
    public CommonResult<PageResult<PolicySetVO>> pagePolicySet(@Valid PolicySetPageVO pageVO) {
        return success(policySetService.pagePolicySet0(pageVO));
    }

    /**
     * 列表查询
     *
     * @return list
     */
    @GetMapping("/list")
    public CommonResult<List<PolicySetVO>> listPolicySet() {
        return success(policySetService.listPolicySet());
    }

    /**
     * 获取策略集lv列表
     *
     * @return lv列表
     */
    @GetMapping("/lvList")
    public CommonResult<List<LabelValue>> getLabelValueList() {
        return success(policySetService.getLabelValueList());
    }

}
