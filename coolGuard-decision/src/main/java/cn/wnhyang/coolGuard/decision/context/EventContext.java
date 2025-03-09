package cn.wnhyang.coolGuard.decision.context;

import cn.wnhyang.coolGuard.decision.entity.Tag;
import cn.wnhyang.coolGuard.decision.vo.result.PolicySetResult;
import lombok.Data;

import java.io.Serial;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * TODO 包含执行流程，策略集/策略/规则详细数据，计算明细，编排
 *
 * @author wnhyang
 * @date 2024/12/8
 **/
@Data
public class EventContext {

    private Set<Tag> tags = new LinkedHashSet<>();

    public void addTag(Tag tag) {
        tags.add(tag);
    }

    @Data
    public static class TagCtx extends Tag {

        @Serial
        private static final long serialVersionUID = 4268648821226830437L;

    }

    private PolicySetResult policySetResult;

}
