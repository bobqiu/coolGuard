package cn.wnhyang.coolGuard.service.impl;

import cn.wnhyang.coolGuard.convert.ChainConvert;
import cn.wnhyang.coolGuard.entity.Chain;
import cn.wnhyang.coolGuard.mapper.ChainMapper;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.ChainService;
import cn.wnhyang.coolGuard.vo.create.ChainCreateVO;
import cn.wnhyang.coolGuard.vo.page.ChainPageVO;
import cn.wnhyang.coolGuard.vo.update.ChainUpdateVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public Long createChain(ChainCreateVO createVO) {
        Chain chain = ChainConvert.INSTANCE.convert(createVO);
        chainMapper.insert(chain);
        return chain.getId();
    }

    @Override
    public void updateChain(ChainUpdateVO updateVO) {
        Chain chain = ChainConvert.INSTANCE.convert(updateVO);
        chainMapper.updateById(chain);
    }

    @Override
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
