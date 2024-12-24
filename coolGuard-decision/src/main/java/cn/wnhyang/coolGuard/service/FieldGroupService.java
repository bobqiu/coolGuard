package cn.wnhyang.coolGuard.service;

import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.FieldGroupVO;
import cn.wnhyang.coolGuard.vo.create.FieldGroupCreateVO;
import cn.wnhyang.coolGuard.vo.page.FieldGroupPageVO;
import cn.wnhyang.coolGuard.vo.update.FieldGroupUpdateVO;

import java.util.List;

/**
 * 字段分组表 服务类
 *
 * @author wnhyang
 * @since 2024/03/14
 */
public interface FieldGroupService {

    /**
     * 新建
     *
     * @param createVO 新建VO
     * @return id
     */
    Long createFieldGroup(FieldGroupCreateVO createVO);

    /**
     * 更新
     *
     * @param updateVO 更新VO
     */
    void updateFieldGroup(FieldGroupUpdateVO updateVO);

    /**
     * 删除
     *
     * @param id id
     */
    void deleteFieldGroup(Long id);

    /**
     * 查询单个
     *
     * @param id id
     * @return po
     */
    FieldGroupVO getFieldGroup(Long id);

    /**
     * 分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    PageResult<FieldGroupVO> pageFieldGroup(FieldGroupPageVO pageVO);

    /**
     * 列表查询
     *
     * @return list
     */
    List<FieldGroupVO> listFieldGroup();
}
