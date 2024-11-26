package cn.wnhyang.coolGuard.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.wnhyang.coolGuard.constant.RedisKey;
import cn.wnhyang.coolGuard.constant.SceneType;
import cn.wnhyang.coolGuard.convert.ApplicationConvert;
import cn.wnhyang.coolGuard.entity.Application;
import cn.wnhyang.coolGuard.entity.Indicator;
import cn.wnhyang.coolGuard.entity.PolicySet;
import cn.wnhyang.coolGuard.mapper.ApplicationMapper;
import cn.wnhyang.coolGuard.mapper.IndicatorMapper;
import cn.wnhyang.coolGuard.mapper.PolicySetMapper;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.ApplicationService;
import cn.wnhyang.coolGuard.vo.create.ApplicationCreateVO;
import cn.wnhyang.coolGuard.vo.page.ApplicationPageVO;
import cn.wnhyang.coolGuard.vo.update.ApplicationUpdateVO;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static cn.wnhyang.coolGuard.exception.ErrorCodes.*;
import static cn.wnhyang.coolGuard.exception.util.ServiceExceptionUtil.exception;

/**
 * 应用表 服务实现类
 *
 * @author wnhyang
 * @since 2024/04/03
 */
@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationMapper applicationMapper;

    private final IndicatorMapper indicatorMapper;
    private final PolicySetMapper policySetMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = RedisKey.APPLICATION, allEntries = true)
    public Long createApplication(ApplicationCreateVO createVO) {
        if (applicationMapper.selectByName(createVO.getName()) != null) {
            throw exception(APPLICATION_NAME_EXIST);
        }
        Application application = ApplicationConvert.INSTANCE.convert(createVO);
        applicationMapper.insert(application);
        return application.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = RedisKey.APPLICATION, allEntries = true)
    public void updateApplication(ApplicationUpdateVO updateVO) {
        Application application = ApplicationConvert.INSTANCE.convert(updateVO);
        applicationMapper.updateById(application);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = RedisKey.APPLICATION, allEntries = true)
    public void deleteApplication(Long id) {
        Application application = applicationMapper.selectById(id);
        if (application == null) {
            throw exception(APPLICATION_NOT_EXIST);
        }
        // 确认是否有指标引用
        List<Indicator> indicatorList = indicatorMapper.selectList(SceneType.APP, application.getName());
        if (CollUtil.isNotEmpty(indicatorList)) {
            throw exception(APPLICATION_REFERENCE_DELETE);
        }
        // 确认是否有策略集引用
        List<PolicySet> policySets = policySetMapper.selectList(null, application.getName(), null, null);
        if (CollUtil.isNotEmpty(policySets)) {
            throw exception(APPLICATION_REFERENCE_DELETE);
        }
        applicationMapper.deleteById(id);
    }

    @Override
    public Application getApplication(Long id) {
        return applicationMapper.selectById(id);
    }

    @Override
    public PageResult<Application> pageApplication(ApplicationPageVO pageVO) {
        return applicationMapper.selectPage(pageVO);
    }

}
