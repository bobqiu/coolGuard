package cn.wnhyang.coolGuard.controller;

import cn.wnhyang.coolGuard.convert.ChainConvert;
import cn.wnhyang.coolGuard.pojo.CommonResult;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.ChainService;
import cn.wnhyang.coolGuard.vo.ChainVO;
import cn.wnhyang.coolGuard.vo.create.ChainCreateVO;
import cn.wnhyang.coolGuard.vo.page.ChainPageVO;
import cn.wnhyang.coolGuard.vo.update.ChainUpdateVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static cn.wnhyang.coolGuard.pojo.CommonResult.success;

/**
 * chain
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@RestController
@RequestMapping("/chain")
@RequiredArgsConstructor
public class ChainController {

    private final ChainService chainService;

    /**
     * 新增
     *
     * @param createVO 创建VO
     * @return id
     */
    @PostMapping
    public CommonResult<Long> createChain(@RequestBody @Valid ChainCreateVO createVO) {
        return success(chainService.createChain(createVO));
    }

    /**
     * 更新
     *
     * @param updateVO 更新VO
     * @return true/false
     */
    @PutMapping
    public CommonResult<Boolean> updateChain(@RequestBody @Valid ChainUpdateVO updateVO) {
        chainService.updateChain(updateVO);
        return success(true);
    }

    /**
     * 删除
     *
     * @param id id
     * @return true/false
     */
    @DeleteMapping
    public CommonResult<Boolean> deleteChain(@RequestParam("id") Long id) {
        chainService.deleteChain(id);
        return success(true);
    }

    /**
     * 查询单个
     *
     * @param id id
     * @return vo
     */
    @GetMapping("/{id}")
    public CommonResult<ChainVO> getChain(@PathVariable("id") Long id) {
        return success(ChainConvert.INSTANCE.convert(chainService.getChain(id)));
    }

    /**
     * 分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    @GetMapping("/page")
    public CommonResult<PageResult<ChainVO>> pageChain(@Valid ChainPageVO pageVO) {
        return success(ChainConvert.INSTANCE.convert(chainService.pageChain(pageVO)));
    }
}
