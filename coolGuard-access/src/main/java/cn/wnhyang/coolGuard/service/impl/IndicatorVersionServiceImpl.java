package cn.wnhyang.coolGuard.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.wnhyang.coolGuard.convert.IndicatorVersionConvert;
import cn.wnhyang.coolGuard.entity.IndicatorVersion;
import cn.wnhyang.coolGuard.mapper.ChainMapper;
import cn.wnhyang.coolGuard.mapper.IndicatorVersionMapper;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.IndicatorVersionService;
import cn.wnhyang.coolGuard.util.JsonUtils;
import cn.wnhyang.coolGuard.util.LFUtil;
import cn.wnhyang.coolGuard.vo.Cond;
import cn.wnhyang.coolGuard.vo.IndicatorVersionVO;
import cn.wnhyang.coolGuard.vo.page.IndicatorVersionPageVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 指标表历史表 服务实现类
 *
 * @author wnhyang
 * @since 2024/11/21
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class IndicatorVersionServiceImpl implements IndicatorVersionService {

    private final IndicatorVersionMapper indicatorVersionMapper;

    private final ChainMapper chainMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        indicatorVersionMapper.deleteById(id);
    }

    @Override
    public IndicatorVersionVO get(Long id) {
        IndicatorVersion indicatorVersion = indicatorVersionMapper.selectById(id);
        IndicatorVersionVO indicatorVersionVO = IndicatorVersionConvert.INSTANCE.convert(indicatorVersion);
        indicatorVersionVO.setCond(JsonUtils.parseObject(indicatorVersionVO.getCondStr(), Cond.class));
        return indicatorVersionVO;
    }

    @Override
    public PageResult<IndicatorVersionVO> page(IndicatorVersionPageVO pageVO) {
        PageResult<IndicatorVersion> indicatorVersionPageResult = indicatorVersionMapper.selectPage(pageVO);
        PageResult<IndicatorVersionVO> voPageResult = IndicatorVersionConvert.INSTANCE.convert(indicatorVersionPageResult);
        voPageResult.getList().forEach(indicatorVO -> indicatorVO.setCond(JsonUtils.parseObject(indicatorVO.getCondStr(), Cond.class)));
        return voPageResult;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void offline(Long id) {
        IndicatorVersion indicatorVersion = indicatorVersionMapper.selectById(id);
        indicatorVersionMapper.updateById(new IndicatorVersion().setId(id).setStatus(Boolean.FALSE));
        chainMapper.deleteByChainName(StrUtil.format(LFUtil.INDICATOR_CHAIN, indicatorVersion.getCode()));
    }

}
