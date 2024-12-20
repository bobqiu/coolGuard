package cn.wnhyang.coolGuard.service.impl;

import cn.wnhyang.coolGuard.context.EventContext;
import cn.wnhyang.coolGuard.convert.TagConvert;
import cn.wnhyang.coolGuard.entity.Action;
import cn.wnhyang.coolGuard.entity.Tag;
import cn.wnhyang.coolGuard.mapper.TagMapper;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.TagService;
import cn.wnhyang.coolGuard.util.LFUtil;
import cn.wnhyang.coolGuard.vo.create.TagCreateVO;
import cn.wnhyang.coolGuard.vo.page.TagPageVO;
import cn.wnhyang.coolGuard.vo.update.TagUpdateVO;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.annotation.LiteflowMethod;
import com.yomahub.liteflow.core.NodeComponent;
import com.yomahub.liteflow.enums.LiteFlowMethodEnum;
import com.yomahub.liteflow.enums.NodeTypeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 标签表 服务实现类
 *
 * @author wnhyang
 * @since 2024/12/08
 */
@Slf4j
@Service
@LiteflowComponent
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagMapper tagMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long create(TagCreateVO createVO) {
        Tag tag = TagConvert.INSTANCE.convert(createVO);
        tagMapper.insert(tag);
        return tag.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(TagUpdateVO updateVO) {
        Tag tag = TagConvert.INSTANCE.convert(updateVO);
        tagMapper.updateById(tag);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        tagMapper.deleteById(id);
    }

    @Override
    public Tag get(Long id) {
        return tagMapper.selectById(id);
    }

    @Override
    public PageResult<Tag> page(TagPageVO pageVO) {
        return tagMapper.selectPage(pageVO);
    }

    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS, nodeId = LFUtil.ADD_TAG, nodeType = NodeTypeEnum.COMMON, nodeName = "加入标签组件")
    public void addTag(NodeComponent bindCmp) {
        // TODO 完善
        log.info("addTag");
        List<Action.AddTag> addTags = bindCmp.getCmpDataList(Action.AddTag.class);
        EventContext eventContext = bindCmp.getContextBean(EventContext.class);
        for (Action.AddTag addTag : addTags) {
            eventContext.addTag(tagMapper.selectByCode(addTag.getCode()));
        }
    }

}
