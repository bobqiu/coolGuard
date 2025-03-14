package cn.wnhyang.coolguard.decision.service;

import cn.wnhyang.coolguard.decision.entity.Cond;

/**
 * 规则条件表 服务类
 *
 * @author wnhyang
 * @since 2024/04/04
 */
public interface CondService {

    /**
     * 条件
     *
     * @param cond 条件
     * @return boolean
     */
    boolean cond(Cond cond);
}
