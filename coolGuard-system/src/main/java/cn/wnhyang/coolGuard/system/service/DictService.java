package cn.wnhyang.coolGuard.system.service;

import cn.wnhyang.coolGuard.entity.LabelValue;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.system.entity.DictDO;
import cn.wnhyang.coolGuard.system.entity.DictData;
import cn.wnhyang.coolGuard.system.vo.dict.DictCreateVO;
import cn.wnhyang.coolGuard.system.vo.dict.DictPageVO;
import cn.wnhyang.coolGuard.system.vo.dict.DictUpdateVO;

import java.util.List;

/**
 * 字典表 服务类
 *
 * @author wnhyang
 * @since 2025/01/03
 */
public interface DictService {

    /**
     * 新建
     *
     * @param createVO 新建VO
     * @return id
     */
    Long create(DictCreateVO createVO);

    /**
     * 更新
     *
     * @param updateVO 更新VO
     */
    void update(DictUpdateVO updateVO);

    /**
     * 删除
     *
     * @param id id
     */
    void delete(Long id);

    /**
     * 查询单个
     *
     * @param id id
     * @return po
     */
    DictDO get(Long id);

    /**
     * 分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    PageResult<DictDO> page(DictPageVO pageVO);

    /**
     * 获取字典列表
     *
     * @return labelValueList
     */
    List<LabelValue> getLabelValueList();

    /**
     * 获取字典数据列表
     *
     * @param value 字典值
     * @return dictDataList
     */
    List<DictData> getDataList(String value);
}
