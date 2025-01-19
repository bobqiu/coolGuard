package cn.wnhyang.coolGuard.service;

import cn.wnhyang.coolGuard.entity.Access;
import cn.wnhyang.coolGuard.entity.FieldRef;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.vo.InputFieldVO;
import cn.wnhyang.coolGuard.vo.OutputFieldVO;
import cn.wnhyang.coolGuard.vo.create.FieldRefCreateVO;
import cn.wnhyang.coolGuard.vo.page.FieldRefPageVO;
import cn.wnhyang.coolGuard.vo.update.FieldRefUpdateVO;

import java.util.List;

/**
 * 字段引用 服务类
 *
 * @author wnhyang
 * @since 2025/01/19
 */
public interface FieldRefService {

    /**
     * 新建
     *
     * @param createVO 新建VO
     * @return id
     */
    Long create(FieldRefCreateVO createVO);

    /**
     * 更新
     *
     * @param updateVO 更新VO
     */
    void update(FieldRefUpdateVO updateVO);

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
    FieldRef get(Long id);

    /**
     * 分页查询
     *
     * @param pageVO 分页VO
     * @return pageResult
     */
    PageResult<FieldRef> page(FieldRefPageVO pageVO);

    /**
     * 复制接入
     *
     * @param sourceCode 源接入编码
     * @param targetCode 目标接入编码
     */
    void copyAccess(String sourceCode, String targetCode);

    /**
     * 获取接入输入字段列表
     *
     * @param access 接入
     * @return 输入字段列表
     */
    List<InputFieldVO> getAccessInputFieldList(Access access);

    /**
     * 获取接入输出字段列表
     *
     * @param access 接入
     * @return 输出字段列表
     */
    List<OutputFieldVO> getAccessOutputFieldList(Access access);
}
