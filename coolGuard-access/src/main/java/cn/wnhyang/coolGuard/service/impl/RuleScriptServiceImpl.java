package cn.wnhyang.coolGuard.service.impl;

import cn.wnhyang.coolGuard.convert.RuleScriptConvert;
import cn.wnhyang.coolGuard.entity.RuleScript;
import cn.wnhyang.coolGuard.mapper.RuleScriptMapper;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.RuleScriptService;
import cn.wnhyang.coolGuard.vo.create.RuleScriptCreateVO;
import cn.wnhyang.coolGuard.vo.page.RuleScriptPageVO;
import cn.wnhyang.coolGuard.vo.update.RuleScriptUpdateVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public Long createRuleScript(RuleScriptCreateVO createVO) {
        RuleScript ruleScript = RuleScriptConvert.INSTANCE.convert(createVO);
        ruleScriptMapper.insert(ruleScript);
        return ruleScript.getId();
    }

    @Override
    public void updateRuleScript(RuleScriptUpdateVO updateVO) {
        RuleScript ruleScript = RuleScriptConvert.INSTANCE.convert(updateVO);
        ruleScriptMapper.updateById(ruleScript);
    }

    @Override
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
