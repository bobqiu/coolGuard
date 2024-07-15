package cn.wnhyang.coolGuard.controller;

import cn.wnhyang.coolGuard.context.AccessResponse;
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
 * 接入配置
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
     * 同步接入
     *
     * @param name   服务名
     * @param params 参数
     * @return map
     */
    @PostMapping("/{name}/sync")
    public CommonResult<AccessResponse> syncRisk(@PathVariable("name") String name, @RequestBody Map<String, String> params) {
        return success(accessService.syncRisk(name, params));
    }

    /**
     * 异步接入
     *
     * @param name   服务名
     * @param params 参数
     * @return map
     */
    @PostMapping("/{name}/async")
    public CommonResult<AccessResponse> asyncRisk(@PathVariable("name") String name, @RequestBody Map<String, String> params) {
        return success(accessService.asyncRisk(name, params));
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
    @GetMapping("/{id}")
    public CommonResult<AccessVO> getAccess(@PathVariable("id") Long id) {
        return success(accessService.getAccess(id));
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

}
