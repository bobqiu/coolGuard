package cn.wnhyang.coolguard.decision.mapper;

import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.decision.entity.Chain;
import cn.wnhyang.coolguard.decision.vo.page.ChainPageVO;
import cn.wnhyang.coolguard.mybatis.mapper.BaseMapperX;
import cn.wnhyang.coolguard.mybatis.wrapper.LambdaQueryWrapperX;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * chain表 Mapper 接口
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@Mapper
public interface ChainMapper extends BaseMapperX<Chain> {

    default PageResult<Chain> selectPage(ChainPageVO pageVO) {
        return selectPage(pageVO, new LambdaQueryWrapperX<Chain>());
    }

    default boolean selectByChainName(String chainName) {
        return selectCount(Chain::getChainName, chainName) > 0;
    }

    default int updateByChainName(String chainName, Chain chain) {
        return update(chain, new LambdaUpdateWrapper<Chain>().eq(Chain::getChainName, chainName));
    }

    default Chain getByChainName(String chainName) {
        return selectOne(Chain::getChainName, chainName);
    }

    default void deleteByChainName(String chainName) {
        delete(new LambdaQueryWrapperX<Chain>().eq(Chain::getChainName, chainName));
    }

    default boolean selectByChainNameAndEl(String chainName, String el) {
        return selectCount(new LambdaQueryWrapperX<Chain>().eq(Chain::getChainName, chainName).like(Chain::getElData, el)) > 0;
    }

    default void updateNewChainNameByOldChainName(String oldChainName, String newChainName) {
        update(new Chain().setChainName(newChainName), new LambdaUpdateWrapper<Chain>().eq(Chain::getChainName, oldChainName));
    }
}
