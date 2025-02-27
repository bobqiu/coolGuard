package cn.wnhyang.coolGuard.service.impl;

import cn.wnhyang.coolGuard.convert.ListSetConvert;
import cn.wnhyang.coolGuard.entity.LabelValue;
import cn.wnhyang.coolGuard.entity.ListSet;
import cn.wnhyang.coolGuard.mapper.ListSetMapper;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.ListSetService;
import cn.wnhyang.coolGuard.util.CollectionUtils;
import cn.wnhyang.coolGuard.vo.create.ListSetCreateVO;
import cn.wnhyang.coolGuard.vo.page.ListSetPageVO;
import cn.wnhyang.coolGuard.vo.update.ListSetUpdateVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 名单集表 服务实现类
 *
 * @author wnhyang
 * @since 2024/05/28
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ListSetServiceImpl implements ListSetService {

    private final ListSetMapper listSetMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long create(ListSetCreateVO createVO) {
        ListSet listSet = ListSetConvert.INSTANCE.convert(createVO);
        listSetMapper.insert(listSet);
        return listSet.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ListSetUpdateVO updateVO) {
        ListSet listSet = ListSetConvert.INSTANCE.convert(updateVO);
        listSetMapper.updateById(listSet);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        listSetMapper.deleteById(id);
    }

    @Override
    public ListSet get(Long id) {
        return listSetMapper.selectById(id);
    }

    @Override
    public PageResult<ListSet> page(ListSetPageVO pageVO) {
        return listSetMapper.selectPage(pageVO);
    }

    @Override
    public List<LabelValue> getLabelValueList() {
        return CollectionUtils.convertList(listSetMapper.selectList(), ListSet::getLabelValue);
    }

}
