package cn.wnhyang.coolguard.decision.service.impl;

import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.decision.convert.RuleScriptConvert;
import cn.wnhyang.coolguard.decision.entity.RuleScript;
import cn.wnhyang.coolguard.decision.mapper.RuleScriptMapper;
import cn.wnhyang.coolguard.decision.service.RuleScriptService;
import cn.wnhyang.coolguard.decision.vo.create.RuleScriptCreateVO;
import cn.wnhyang.coolguard.decision.vo.page.RuleScriptPageVO;
import cn.wnhyang.coolguard.decision.vo.update.RuleScriptUpdateVO;
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
