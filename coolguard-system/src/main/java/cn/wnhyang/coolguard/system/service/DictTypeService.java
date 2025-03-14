package cn.wnhyang.coolguard.system.service;


import cn.wnhyang.coolguard.common.entity.LabelValue;
import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.system.entity.DictTypeDO;
import cn.wnhyang.coolguard.system.vo.dicttype.DictTypeCreateVO;
import cn.wnhyang.coolguard.system.vo.dicttype.DictTypePageVO;
import cn.wnhyang.coolguard.system.vo.dicttype.DictTypeUpdateVO;

import java.util.List;

/**
 * 字典类型表 服务类
 *
 * @author wnhyang
 * @since 2023/09/13
 */
public interface DictTypeService {

    /**
     * 创建字典类型
     *
     * @param reqVO 字典类型
     * @return 字典类型id
     */
    Long createDictType(DictTypeCreateVO reqVO);

    /**
     * 更新字典类型
     *
     * @param reqVO 字典类型
     */
    void updateDictType(DictTypeUpdateVO reqVO);

    /**
     * 删除字典类型
     *
     * @param id 字典类型id
     */
    void deleteDictType(Long id);

    /**
     * 分页查询字典类型
     *
     * @param reqVO 分页请求
     * @return 分页结果
     */
    PageResult<DictTypeDO> getDictTypePage(DictTypePageVO reqVO);

    /**
     * 字典类型
     *
     * @param id 字典类型id
     * @return 字典类型
     */
    DictTypeDO getDictType(Long id);

    /**
     * 获取字典类型列表
     *
     * @return 字典类型列表
     */
    List<LabelValue> getLabelValueList();
}
