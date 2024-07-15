package cn.wnhyang.coolGuard.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.wnhyang.coolGuard.context.PolicyContext;
import cn.wnhyang.coolGuard.convert.PolicyConvert;
import cn.wnhyang.coolGuard.entity.Policy;
import cn.wnhyang.coolGuard.entity.Rule;
import cn.wnhyang.coolGuard.enums.PolicyMode;
import cn.wnhyang.coolGuard.mapper.PolicyMapper;
import cn.wnhyang.coolGuard.mapper.RuleMapper;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.PolicyService;
import cn.wnhyang.coolGuard.vo.PolicyVO;
import cn.wnhyang.coolGuard.vo.create.PolicyCreateVO;
import cn.wnhyang.coolGuard.vo.page.PolicyPageVO;
import cn.wnhyang.coolGuard.vo.update.PolicyUpdateVO;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.annotation.LiteflowMethod;
import com.yomahub.liteflow.core.NodeComponent;
import com.yomahub.liteflow.enums.LiteFlowMethodEnum;
import com.yomahub.liteflow.enums.NodeTypeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static cn.wnhyang.coolGuard.exception.ErrorCodes.POLICY_CODE_EXIST;
import static cn.wnhyang.coolGuard.exception.ErrorCodes.POLICY_NOT_EXIST;
import static cn.wnhyang.coolGuard.exception.util.ServiceExceptionUtil.exception;

/**
 * 策略表 服务实现类
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@Slf4j
@Service
@LiteflowComponent
@RequiredArgsConstructor
public class PolicyServiceImpl implements PolicyService {

    private final PolicyMapper policyMapper;

    private final RuleMapper ruleMapper;

    @Override
    public Long createPolicy(PolicyCreateVO createVO) {
        validateForCreateOrUpdate(null, createVO.getCode());
        Policy policy = PolicyConvert.INSTANCE.convert(createVO);
        policyMapper.insert(policy);
        return policy.getId();
    }

    @Override
    public void updatePolicy(PolicyUpdateVO updateVO) {
        Policy policy = PolicyConvert.INSTANCE.convert(updateVO);
        policyMapper.updateById(policy);
    }

    @Override
    public void deletePolicy(Long id) {
        // TODO 有引用不可删除
        validateExists(id);
        policyMapper.deleteById(id);
    }

    @Override
    public Policy getPolicy(Long id) {
        return policyMapper.selectById(id);
    }

    @Override
    public PageResult<Policy> pagePolicy(PolicyPageVO pageVO) {
        return policyMapper.selectPage(pageVO);
    }

    // TODO 未来替换成路由
    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS, nodeId = "policyProcess", nodeType = NodeTypeEnum.COMMON)
    public void policyProcess(NodeComponent bindCmp) {
        String tag = bindCmp.getTag();
        PolicyContext policyContext = bindCmp.getContextBean(PolicyContext.class);
        PolicyVO policy = policyContext.getPolicy(Long.valueOf(tag));
        log.info("当前策略(id:{}, name:{}, code:{})", policy.getId(), policy.getName(), policy.getCode());

        // TODO 路由规则运行

        List<Rule> ruleList = ruleMapper.selectByPolicyId(policy.getId());
        // TODO 开启和模拟状态规则都要运行
        if (CollUtil.isEmpty(ruleList)) {
            log.info("策略(name:{})下没有规则运行", policy.getName());
            return;
        }
        policyContext.initRuleList(policy.getId());
        PolicyMode byMode = PolicyMode.getByMode(policy.getMode());


        bindCmp.invoke2Resp(policy.getChainName(), null);

    }

    @LiteflowMethod(value = LiteFlowMethodEnum.IS_END, nodeId = "policyProcess", nodeType = NodeTypeEnum.COMMON)
    public boolean policyProcessEnd(NodeComponent bindCmp) {
        // isEnd 用于顺序模式，提前终止流程
        log.info("终止");
        return false;
    }

    private void validateForCreateOrUpdate(Long id, String name) {
        // 校验存在
        validateExists(id);
        // 校验名唯一
        validateCodeUnique(id, name);
    }

    private void validateExists(Long id) {
        if (id == null) {
            return;
        }
        Policy policy = policyMapper.selectById(id);
        if (policy == null) {
            throw exception(POLICY_NOT_EXIST);
        }
    }

    private void validateCodeUnique(Long id, String code) {
        if (StrUtil.isBlank(code)) {
            return;
        }
        Policy policy = policyMapper.selectByCode(code);
        if (policy == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的用户
        if (id == null) {
            throw exception(POLICY_CODE_EXIST);
        }
        if (!policy.getId().equals(id)) {
            throw exception(POLICY_CODE_EXIST);
        }
    }

}
