package cn.wnhyang.coolGuard.controller;

import cn.wnhyang.coolGuard.convert.AccessConvert;
import cn.wnhyang.coolGuard.pojo.CommonResult;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.AccessService;
import cn.wnhyang.coolGuard.vo.AccessVO;
import cn.wnhyang.coolGuard.vo.create.AccessCreateVO;
import cn.wnhyang.coolGuard.vo.page.AccessPageVO;
import cn.wnhyang.coolGuard.vo.update.AccessUpdateVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static cn.wnhyang.coolGuard.pojo.CommonResult.success;

/**
 * 接入
 *
 * @author wnhyang
 * @since 2024/03/14
 */
@RestController
@RequestMapping("/access")
@RequiredArgsConstructor
public class AccessController {

    private final AccessService accessService;

    /**
     * 测试接入
     *
     * @param code   服务名
     * @param params 参数
     * @return map
     */
    @PostMapping("/test/{code}")
    public CommonResult<Map<String, Object>> risk(@PathVariable("code") String code, @RequestBody Map<String, String> params) {
        return success(accessService.test(code, params));
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
        return success(accessService.syncRisk(code, params));
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
        return success(accessService.asyncRisk(code, params));
    }

    /**
     * 新增
     *
     * @param createVO 创建VO
     * @return id
     */
    @PostMapping
    public CommonResult<Long> createAccess(@RequestBody @Valid AccessCreateVO createVO) {
        return success(accessService.createAccess(createVO));
    }

    /**
     * 更新
     *
     * @param updateVO 更新VO
     * @return true/false
     */
    @PutMapping
    public CommonResult<Boolean> updateAccess(@RequestBody @Valid AccessUpdateVO updateVO) {
        accessService.updateAccess(updateVO);
        return success(true);
    }

    /**
     * 删除
     *
     * @param id id
     * @return true/false
     */
    @DeleteMapping
    public CommonResult<Boolean> deleteAccess(@RequestParam("id") Long id) {
        accessService.deleteAccess(id);
        return success(true);
    }

    /**
     * 查询单个
     *
     * @param id id
     * @return vo
     */
    @GetMapping
    public CommonResult<AccessVO> getAccess(@RequestParam("id") Long id) {
        return success(accessService.getAccess(id));
    }

    /**
     * 查询单个
     *
     * @param code code
     * @return vo
     */
    @GetMapping("/code")
    public CommonResult<AccessVO> getAccessByCode(@RequestParam("code") String code) {
        return success(AccessConvert.INSTANCE.convert(accessService.getAccessByCode(code)));
    }

    /**
     * 分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    @GetMapping("/page")
    public CommonResult<PageResult<AccessVO>> pageAccess(@Valid AccessPageVO pageVO) {
        return success(accessService.pageAccess(pageVO));
    }

    /**
     * 复制接入
     *
     * @param id 接入id
     * @return 结果
     */
    @PostMapping("/copy")
    public CommonResult<Long> copyAccess(@RequestParam("id") Long id) {
        return success(accessService.copyAccess(id));
    }

}
