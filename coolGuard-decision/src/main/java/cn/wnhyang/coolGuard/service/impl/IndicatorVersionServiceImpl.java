package cn.wnhyang.coolGuard.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.wnhyang.coolGuard.convert.IndicatorConvert;
import cn.wnhyang.coolGuard.convert.IndicatorVersionConvert;
import cn.wnhyang.coolGuard.entity.Indicator;
import cn.wnhyang.coolGuard.entity.IndicatorVersion;
import cn.wnhyang.coolGuard.entity.LabelValue;
import cn.wnhyang.coolGuard.mapper.ChainMapper;
import cn.wnhyang.coolGuard.mapper.IndicatorMapper;
import cn.wnhyang.coolGuard.mapper.IndicatorVersionMapper;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.IndicatorVersionService;
import cn.wnhyang.coolGuard.util.CollectionUtils;
import cn.wnhyang.coolGuard.util.LFUtil;
import cn.wnhyang.coolGuard.vo.IndicatorSimpleVO;
import cn.wnhyang.coolGuard.vo.IndicatorVersionVO;
import cn.wnhyang.coolGuard.vo.page.IndicatorVersionPageVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static cn.wnhyang.coolGuard.error.DecisionErrorCode.INDICATOR_NAME_EXIST;
import static cn.wnhyang.coolGuard.error.DecisionErrorCode.INDICATOR_VERSION_NOT_EXIST;
import static cn.wnhyang.coolGuard.exception.util.ServiceExceptionUtil.exception;

/**
 * 指标表版本表 服务实现类
 *
 * @author wnhyang
 * @since 2024/11/21
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class IndicatorVersionServiceImpl implements IndicatorVersionService {

    private final IndicatorVersionMapper indicatorVersionMapper;

    private final IndicatorMapper indicatorMapper;

    private final ChainMapper chainMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        IndicatorVersion indicatorVersion = indicatorVersionMapper.selectById(id);
        if (indicatorVersion == null) {
            throw exception(INDICATOR_VERSION_NOT_EXIST);
        }
        indicatorVersionMapper.deleteById(id);
    }

    @Override
    public IndicatorVersionVO get(Long id) {
        IndicatorVersion indicatorVersion = indicatorVersionMapper.selectById(id);
        return IndicatorVersionConvert.INSTANCE.convert(indicatorVersion);
    }

    @Override
    public IndicatorVersionVO getByCode(String code) {
        IndicatorVersion indicatorVersion = indicatorVersionMapper.selectByCode(code);
        return IndicatorVersionConvert.INSTANCE.convert(indicatorVersion);
    }

    @Override
    public PageResult<IndicatorVersionVO> page(IndicatorVersionPageVO pageVO) {
        PageResult<IndicatorVersion> indicatorVersionPageResult = indicatorVersionMapper.selectPage(pageVO);
        return IndicatorVersionConvert.INSTANCE.convert(indicatorVersionPageResult);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void offline(Long id) {
        IndicatorVersion indicatorVersion = indicatorVersionMapper.selectById(id);
        if (indicatorVersion == null) {
            throw exception(INDICATOR_VERSION_NOT_EXIST);
        }
        indicatorMapper.updateByCode(new Indicator().setCode(indicatorVersion.getCode()).setPublish(Boolean.FALSE));
        indicatorVersionMapper.updateById(new IndicatorVersion().setId(id).setLatest(Boolean.FALSE));
        chainMapper.deleteByChainName(StrUtil.format(LFUtil.INDICATOR_CHAIN, indicatorVersion.getCode()));
    }

    @Override
    public PageResult<IndicatorVersionVO> pageByCode(IndicatorVersionPageVO pageVO) {
        PageResult<IndicatorVersion> indicatorVersionPageResult = indicatorVersionMapper.selectPageByCode(pageVO);
        return IndicatorVersionConvert.INSTANCE.convert(indicatorVersionPageResult);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void chose(Long id) {
        IndicatorVersion indicatorVersion = indicatorVersionMapper.selectById(id);
        if (indicatorVersion == null) {
            throw exception(INDICATOR_VERSION_NOT_EXIST);
        }
        Indicator indicator = indicatorMapper.selectByCode(indicatorVersion.getCode());
        Indicator byName = indicatorMapper.selectByName(indicatorVersion.getName());
        if (byName != null && !indicator.getId().equals(byName.getId())) {
            throw exception(INDICATOR_NAME_EXIST);
        }
        Indicator convert = IndicatorConvert.INSTANCE.convert(indicatorVersion);
        convert.setPublish(Boolean.FALSE);
        indicatorMapper.updateByCode(convert);
    }

    @Override
    public List<LabelValue> getLabelValueList() {
        return CollectionUtils.convertList(indicatorVersionMapper.selectLatestList(), IndicatorVersion::getLabelValue);
    }

    @Override
    public List<IndicatorSimpleVO> getSimpleList() {
        return IndicatorVersionConvert.INSTANCE.convert(indicatorVersionMapper.selectLatestList());
    }

}
