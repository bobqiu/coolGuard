package cn.wnhyang.coolGuard.decision.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.wnhyang.coolGuard.common.pojo.CommonResult;
import cn.wnhyang.coolGuard.common.pojo.PageResult;
import cn.wnhyang.coolGuard.decision.convert.ChainConvert;
import cn.wnhyang.coolGuard.decision.entity.Node;
import cn.wnhyang.coolGuard.decision.service.ChainService;
import cn.wnhyang.coolGuard.decision.vo.ChainVO;
import cn.wnhyang.coolGuard.decision.vo.create.ChainCreateVO;
import cn.wnhyang.coolGuard.decision.vo.page.ChainPageVO;
import cn.wnhyang.coolGuard.decision.vo.update.ChainUpdateVO;
import cn.wnhyang.coolGuard.log.annotation.OperateLog;
import cn.wnhyang.coolGuard.log.enums.OperateType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static cn.wnhyang.coolGuard.common.pojo.CommonResult.success;

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

    private static final List<Node> NODE_LIST = List.of(
    );

    private final ChainService chainService;

    /**
     * 新增
     *
     * @param createVO 创建VO
     * @return id
     */
    @PostMapping
    @SaCheckPermission("decision:chain:create")
    @OperateLog(module = "后台-决策链", name = "创建决策链", type = OperateType.CREATE)
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
    @SaCheckPermission("decision:chain:update")
    @OperateLog(module = "后台-决策链", name = "更新决策链", type = OperateType.UPDATE)
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
    @SaCheckPermission("decision:chain:delete")
    @OperateLog(module = "后台-决策链", name = "删除决策链", type = OperateType.DELETE)
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
    @GetMapping
    public CommonResult<ChainVO> getChain(@RequestParam("id") Long id) {
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

    /**
     * 获取所有节点
     *
     * @return 节点列表
     */
    @GetMapping("/nodes")
    public CommonResult<List<Node>> nodeList() {
        return success(NODE_LIST);
    }

}
