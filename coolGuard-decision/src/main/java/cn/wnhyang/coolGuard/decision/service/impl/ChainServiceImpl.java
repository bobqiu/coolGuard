package cn.wnhyang.coolGuard.decision.service.impl;

import cn.wnhyang.coolGuard.common.pojo.PageResult;
import cn.wnhyang.coolGuard.decision.convert.ChainConvert;
import cn.wnhyang.coolGuard.decision.entity.Chain;
import cn.wnhyang.coolGuard.decision.mapper.ChainMapper;
import cn.wnhyang.coolGuard.decision.service.ChainService;
import cn.wnhyang.coolGuard.decision.vo.create.ChainCreateVO;
import cn.wnhyang.coolGuard.decision.vo.page.ChainPageVO;
import cn.wnhyang.coolGuard.decision.vo.update.ChainUpdateVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * chain表 服务实现类
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@Service
@RequiredArgsConstructor
public class ChainServiceImpl implements ChainService {

    private final ChainMapper chainMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createChain(ChainCreateVO createVO) {
        Chain chain = ChainConvert.INSTANCE.convert(createVO);
        chainMapper.insert(chain);
        return chain.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateChain(ChainUpdateVO updateVO) {
        Chain chain = ChainConvert.INSTANCE.convert(updateVO);
        chainMapper.updateById(chain);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteChain(Long id) {
        chainMapper.deleteById(id);
    }

    @Override
    public Chain getChain(Long id) {
        return chainMapper.selectById(id);
    }

    @Override
    public PageResult<Chain> pageChain(ChainPageVO pageVO) {
        return chainMapper.selectPage(pageVO);
    }

}
