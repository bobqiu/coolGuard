package cn.wnhyang.coolGuard.service.impl;

import cn.wnhyang.coolGuard.convert.ListDataConvert;
import cn.wnhyang.coolGuard.entity.ListData;
import cn.wnhyang.coolGuard.mapper.ListDataMapper;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.ListDataService;
import cn.wnhyang.coolGuard.vo.create.ListDataCreateVO;
import cn.wnhyang.coolGuard.vo.page.ListDataPageVO;
import cn.wnhyang.coolGuard.vo.update.ListDataUpdateVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 名单数据表 服务实现类
 *
 * @author wnhyang
 * @since 2024/05/28
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ListDataServiceImpl implements ListDataService {

    private final ListDataMapper listDataMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long create(ListDataCreateVO createVO) {
        ListData listData = ListDataConvert.INSTANCE.convert(createVO);
        listDataMapper.insert(listData);
        return listData.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ListDataUpdateVO updateVO) {
        ListData listData = ListDataConvert.INSTANCE.convert(updateVO);
        listDataMapper.updateById(listData);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        listDataMapper.deleteById(id);
    }

    @Override
    public ListData get(Long id) {
        return listDataMapper.selectById(id);
    }

    @Override
    public PageResult<ListData> page(ListDataPageVO pageVO) {
        return listDataMapper.selectPage(pageVO);
    }

}
