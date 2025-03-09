package cn.wnhyang.coolGuard.decision.service.impl;

import cn.wnhyang.coolGuard.common.pojo.PageResult;
import cn.wnhyang.coolGuard.decision.convert.RuleScriptConvert;
import cn.wnhyang.coolGuard.decision.entity.RuleScript;
import cn.wnhyang.coolGuard.decision.mapper.RuleScriptMapper;
import cn.wnhyang.coolGuard.decision.service.RuleScriptService;
import cn.wnhyang.coolGuard.decision.vo.create.RuleScriptCreateVO;
import cn.wnhyang.coolGuard.decision.vo.page.RuleScriptPageVO;
import cn.wnhyang.coolGuard.decision.vo.update.RuleScriptUpdateVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 规则脚本表 服务实现类
 *
 * @author wnhyang
 * @since 2024/04/03
 */
@Service
@RequiredArgsConstructor
public class RuleScriptServiceImpl implements RuleScriptService {

    private final RuleScriptMapper ruleScriptMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createRuleScript(RuleScriptCreateVO createVO) {
        RuleScript ruleScript = RuleScriptConvert.INSTANCE.convert(createVO);
        ruleScriptMapper.insert(ruleScript);
        return ruleScript.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRuleScript(RuleScriptUpdateVO updateVO) {
        RuleScript ruleScript = RuleScriptConvert.INSTANCE.convert(updateVO);
        ruleScriptMapper.updateById(ruleScript);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRuleScript(Long id) {
        ruleScriptMapper.deleteById(id);
    }

    @Override
    public RuleScript getRuleScript(Long id) {
        return ruleScriptMapper.selectById(id);
    }

    @Override
    public PageResult<RuleScript> pageRuleScript(RuleScriptPageVO pageVO) {
        return ruleScriptMapper.selectPage(pageVO);
    }

}
