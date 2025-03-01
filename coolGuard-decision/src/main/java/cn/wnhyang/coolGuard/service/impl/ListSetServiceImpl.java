package cn.wnhyang.coolGuard.service.impl;

import cn.wnhyang.coolGuard.convert.ListSetConvert;
import cn.wnhyang.coolGuard.entity.LabelValue;
import cn.wnhyang.coolGuard.entity.ListSet;
import cn.wnhyang.coolGuard.mapper.ListDataMapper;
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

import static cn.wnhyang.coolGuard.error.DecisionErrorCode.*;
import static cn.wnhyang.coolGuard.exception.util.ServiceExceptionUtil.exception;

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

    private final ListDataMapper listDataMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long create(ListSetCreateVO createVO) {
        if (listSetMapper.selectByCode(createVO.getCode()) != null) {
            throw exception(LIST_SET_CODE_EXIST);
        }
        ListSet listSet = ListSetConvert.INSTANCE.convert(createVO);
        listSetMapper.insert(listSet);
        return listSet.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ListSetUpdateVO updateVO) {
        ListSet listSet = listSetMapper.selectById(updateVO.getId());
        if (listSet == null) {
            throw exception(LIST_SET_NOE_EXIST);
        }
        ListSet convert = ListSetConvert.INSTANCE.convert(updateVO);
        listSetMapper.updateById(convert);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        ListSet listSet = listSetMapper.selectById(id);
        if (listSet == null) {
            throw exception(LIST_SET_NOE_EXIST);
        }
        Long count = listDataMapper.selectCountBySetCode(listSet.getCode());
        if (count > 0) {
            throw exception(LIST_SET_HAS_DATA);
        }
        // TODO 引用
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
