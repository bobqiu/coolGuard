package cn.wnhyang.coolGuard.context;

import cn.wnhyang.coolGuard.entity.Tag;
import cn.wnhyang.coolGuard.vo.result.PolicySetResult;
import lombok.Data;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 包含执行流程，策略集/策略/规则详细数据
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

    public static class TagCtx {

    }

    private PolicySetResult policySetResult;

}
