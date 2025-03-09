package cn.wnhyang.coolGuard.decision.service.impl;

import cn.wnhyang.coolGuard.common.pojo.PageResult;
import cn.wnhyang.coolGuard.decision.convert.AccessConvert;
import cn.wnhyang.coolGuard.decision.entity.Access;
import cn.wnhyang.coolGuard.decision.entity.Field;
import cn.wnhyang.coolGuard.decision.entity.ParamConfig;
import cn.wnhyang.coolGuard.decision.mapper.AccessMapper;
import cn.wnhyang.coolGuard.decision.service.AccessService;
import cn.wnhyang.coolGuard.decision.service.FieldService;
import cn.wnhyang.coolGuard.decision.vo.AccessVO;
import cn.wnhyang.coolGuard.decision.vo.create.AccessCreateVO;
import cn.wnhyang.coolGuard.decision.vo.page.AccessPageVO;
import cn.wnhyang.coolGuard.decision.vo.update.AccessUpdateVO;
import cn.wnhyang.coolGuard.redis.constant.RedisKey;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static cn.wnhyang.coolGuard.common.exception.util.ServiceExceptionUtil.exception;
import static cn.wnhyang.coolGuard.decision.error.DecisionErrorCode.ACCESS_CODE_EXIST;
import static cn.wnhyang.coolGuard.decision.error.DecisionErrorCode.ACCESS_NOT_EXIST;

/**
 * 接入表 服务实现类
 *
 * @author wnhyang
 * @since 2024/03/14
 */
@Slf4j
@Service
@LiteflowComponent
@RequiredArgsConstructor
public class AccessServiceImpl implements AccessService {

    private final AccessMapper accessMapper;

    private final FieldService fieldService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createAccess(AccessCreateVO createVO) {
        // 1、校验服务name唯一性
        if (accessMapper.selectByCode(createVO.getCode()) != null) {
            throw exception(ACCESS_CODE_EXIST);
        }
        Access access = AccessConvert.INSTANCE.convert(createVO);
        accessMapper.insert(access);
        return access.getId();
    }

    @Override
    @CacheEvict(value = RedisKey.ACCESS, allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void updateAccess(AccessUpdateVO updateVO) {
        Access access = accessMapper.selectById(updateVO.getId());
        if (access == null) {
            throw exception(ACCESS_NOT_EXIST);
        }
        Access convert = AccessConvert.INSTANCE.convert(updateVO);
        accessMapper.updateById(convert);
    }

    @Override
    @CacheEvict(value = RedisKey.ACCESS, allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void deleteAccess(Long id) {
        Access access = accessMapper.selectById(id);
        if (access == null) {
            throw exception(ACCESS_NOT_EXIST);
        }
        accessMapper.deleteById(id);
    }

    @Override
    @Cacheable(value = RedisKey.ACCESS, key = "#id", unless = "#result == null")
    public AccessVO getAccess(Long id) {
        Access access = accessMapper.selectById(id);
        if (access == null) {
            throw exception(ACCESS_NOT_EXIST);
        }
        AccessVO accessVO = AccessConvert.INSTANCE.convert(access);
        fillField(accessVO);
        return accessVO;
    }

    @Override
    public PageResult<AccessVO> pageAccess(AccessPageVO pageVO) {
        PageResult<Access> pageResult = accessMapper.selectPage(pageVO);
        return AccessConvert.INSTANCE.convert(pageResult);
    }

    @Override
    @Cacheable(value = RedisKey.ACCESS, key = "#code", unless = "#result == null")
    public AccessVO getAccessByCode(String code) {
        Access access = accessMapper.selectByCode(code);
        if (access == null) {
            throw exception(ACCESS_NOT_EXIST);
        }
        AccessVO accessVO = AccessConvert.INSTANCE.convert(access);
        fillField(accessVO);
        return accessVO;
    }

    private void fillField(AccessVO accessVO) {
        Map<String, Field> fields = fieldService.getFields();
        accessVO.getInputFieldList().forEach(inputField -> {
            fillField(inputField, fields);
        });
        accessVO.getOutputFieldList().forEach(outputField -> {
            fillField(outputField, fields);
        });
    }

    private void fillField(ParamConfig paramConfig, Map<String, Field> fields) {
        Field field = fields.get(paramConfig.getFieldCode());
        if (field != null) {
            paramConfig.setName(field.getName());
            paramConfig.setType(field.getType());
            paramConfig.setDynamic(field.getDynamic());
            paramConfig.setScript(field.getScript());
            paramConfig.setInfo(field.getInfo());
        }
    }

}
