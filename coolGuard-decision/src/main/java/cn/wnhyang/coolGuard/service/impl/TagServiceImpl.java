package cn.wnhyang.coolGuard.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.wnhyang.coolGuard.context.DecisionContextHolder;
import cn.wnhyang.coolGuard.context.EventContext;
import cn.wnhyang.coolGuard.convert.TagConvert;
import cn.wnhyang.coolGuard.entity.LabelValue;
import cn.wnhyang.coolGuard.entity.Tag;
import cn.wnhyang.coolGuard.mapper.TagMapper;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.TagService;
import cn.wnhyang.coolGuard.util.CollectionUtils;
import cn.wnhyang.coolGuard.vo.create.TagCreateVO;
import cn.wnhyang.coolGuard.vo.page.TagPageVO;
import cn.wnhyang.coolGuard.vo.update.TagUpdateVO;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static cn.wnhyang.coolGuard.error.DecisionErrorCode.TAG_CODE_EXIST;
import static cn.wnhyang.coolGuard.error.DecisionErrorCode.TAG_NOT_EXIST;
import static cn.wnhyang.coolGuard.exception.util.ServiceExceptionUtil.exception;

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
        if (tagMapper.selectByCode(createVO.getCode()) != null) {
            throw exception(TAG_CODE_EXIST);
        }
        Tag tag = TagConvert.INSTANCE.convert(createVO);
        tagMapper.insert(tag);
        return tag.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(TagUpdateVO updateVO) {
        Tag tag = tagMapper.selectById(updateVO.getId());
        if (tag == null) {
            throw exception(TAG_NOT_EXIST);
        }
        Tag convert = TagConvert.INSTANCE.convert(updateVO);
        tagMapper.updateById(convert);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        Tag tag = tagMapper.selectById(id);
        if (tag == null) {
            throw exception(TAG_NOT_EXIST);
        }
        // TODO 引用
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

    @Override
    public List<LabelValue> getLabelValueList() {
        return CollectionUtils.convertList(tagMapper.selectList(), Tag::getLabelValue);
    }

    @Override
    public void addTag(List<String> tagCodes) {
        if (CollUtil.isEmpty(tagCodes)) {
            return;
        }
        EventContext eventContext = DecisionContextHolder.getEventContext();
        // TODO 完善
        log.info("addTag:{}", tagCodes);
        for (String tagCode : tagCodes) {
            eventContext.addTag(tagMapper.selectByCode(tagCode));
        }
    }

}
