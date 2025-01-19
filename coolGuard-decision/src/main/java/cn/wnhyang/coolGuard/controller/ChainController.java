package cn.wnhyang.coolGuard.controller;

import cn.wnhyang.coolGuard.convert.ChainConvert;
import cn.wnhyang.coolGuard.entity.Node;
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

import java.util.List;

import static cn.wnhyang.coolGuard.pojo.CommonResult.success;
import static cn.wnhyang.coolGuard.util.LFUtil.*;

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
            new Node(INDICATOR_FOR_NODE, "入参组件"),
            new Node(INDICATOR_COMMON_NODE, "入参组件"),
            new Node(INDICATOR_TRUE_COMMON_NODE, "入参组件"),
            new Node(INDICATOR_FALSE_COMMON_NODE, "入参组件"),
            new Node(POLICY_SET_COMMON_NODE, "入参组件"),
            new Node(POLICY_COMMON_NODE, "入参组件"),
            new Node(POLICY_FOR_NODE, "入参组件"),
            new Node(POLICY_BREAK_NODE, "入参组件"),
            new Node(COND, "入参组件"),
            new Node(RULE_COMMON_NODE, "入参组件"),
            new Node(RULE_TRUE, "入参组件"),
            new Node(RULE_FALSE, "入参组件")
    );

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
