package cn.wnhyang.coolGuard.controller;

import cn.wnhyang.coolGuard.convert.ServiceConfigFieldConvert;
import cn.wnhyang.coolGuard.pojo.CommonResult;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.ServiceConfigFieldService;
import cn.wnhyang.coolGuard.vo.ServiceConfigFieldVO;
import cn.wnhyang.coolGuard.vo.create.ServiceConfigFieldCreateVO;
import cn.wnhyang.coolGuard.vo.page.ServiceConfigFieldPageVO;
import cn.wnhyang.coolGuard.vo.update.ServiceConfigFieldUpdateVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static cn.wnhyang.coolGuard.pojo.CommonResult.success;

/**
 * 服务配置字段
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@RestController
@RequestMapping("/serviceConfigField")
@RequiredArgsConstructor
public class ServiceConfigFieldController {

    private final ServiceConfigFieldService serviceConfigFieldService;

    /**
     * 新增
     *
     * @param createVO 创建VO
     * @return id
     */
    @PostMapping
    public CommonResult<Long> createServiceConfigField(@RequestBody @Valid ServiceConfigFieldCreateVO createVO) {
        return success(serviceConfigFieldService.createServiceConfigField(createVO));
    }

    /**
     * 更新
     *
     * @param updateVO 更新VO
     * @return true/false
     */
    @PutMapping
    public CommonResult<Boolean> updateServiceConfigField(@RequestBody @Valid ServiceConfigFieldUpdateVO updateVO) {
        serviceConfigFieldService.updateServiceConfigField(updateVO);
        return success(true);
    }

    /**
     * 删除
     *
     * @param id id
     * @return true/false
     */
    @DeleteMapping
    public CommonResult<Boolean> deleteServiceConfigField(@RequestParam("id") Long id) {
        serviceConfigFieldService.deleteServiceConfigField(id);
        return success(true);
    }

    /**
     * 查询单个
     *
     * @param id id
     * @return vo
     */
    @GetMapping("/{id}")
    public CommonResult<ServiceConfigFieldVO> getServiceConfigField(@PathVariable("id") Long id) {
        return success(ServiceConfigFieldConvert.INSTANCE.convert(serviceConfigFieldService.getServiceConfigField(id)));
    }

    /**
     * 分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    @GetMapping("/page")
    public CommonResult<PageResult<ServiceConfigFieldVO>> pageServiceConfigField(@Valid ServiceConfigFieldPageVO pageVO) {
        return success(ServiceConfigFieldConvert.INSTANCE.convert(serviceConfigFieldService.pageServiceConfigField(pageVO)));
    }
}
