package cn.wnhyang.coolGuard.controller;

import cn.wnhyang.coolGuard.convert.ServiceConfigConvert;
import cn.wnhyang.coolGuard.pojo.CommonResult;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.ServiceConfigService;
import cn.wnhyang.coolGuard.vo.ServiceConfigVO;
import cn.wnhyang.coolGuard.vo.create.ServiceConfigCreateVO;
import cn.wnhyang.coolGuard.vo.page.ServiceConfigPageVO;
import cn.wnhyang.coolGuard.vo.update.ServiceConfigUpdateVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static cn.wnhyang.coolGuard.pojo.CommonResult.success;

/**
 * 服务配置
 *
 * @author wnhyang
 * @since 2024/03/14
 */
@RestController
@RequestMapping("/serviceConfig")
@RequiredArgsConstructor
public class ServiceConfigController {

    private final ServiceConfigService serviceConfigService;

    /**
     * 新增
     *
     * @param createVO 创建VO
     * @return id
     */
    @PostMapping
    public CommonResult<Long> createServiceConfig(@RequestBody @Valid ServiceConfigCreateVO createVO) {
        return success(serviceConfigService.createServiceConfig(createVO));
    }

    /**
     * 更新
     *
     * @param updateVO 更新VO
     * @return true/false
     */
    @PutMapping
    public CommonResult<Boolean> updateServiceConfig(@RequestBody @Valid ServiceConfigUpdateVO updateVO) {
        serviceConfigService.updateServiceConfig(updateVO);
        return success(true);
    }

    /**
     * 删除
     *
     * @param id id
     * @return true/false
     */
    @DeleteMapping
    public CommonResult<Boolean> deleteServiceConfig(@RequestParam("id") Long id) {
        serviceConfigService.deleteServiceConfig(id);
        return success(true);
    }

    /**
     * 查询单个
     *
     * @param id id
     * @return vo
     */
    @GetMapping("/{id}")
    public CommonResult<ServiceConfigVO> getServiceConfig(@PathVariable("id") Long id) {
        return success(ServiceConfigConvert.INSTANCE.convert(serviceConfigService.getServiceConfig(id)));
    }

    /**
     * 分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    @GetMapping("/page")
    public CommonResult<PageResult<ServiceConfigVO>> pageServiceConfig(@Valid ServiceConfigPageVO pageVO) {
        return success(ServiceConfigConvert.INSTANCE.convert(serviceConfigService.pageServiceConfig(pageVO)));
    }
}
