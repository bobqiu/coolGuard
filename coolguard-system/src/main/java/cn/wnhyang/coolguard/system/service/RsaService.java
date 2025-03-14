package cn.wnhyang.coolguard.system.service;


import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.system.entity.RsaDO;
import cn.wnhyang.coolguard.system.vo.rsa.RsaCreateVO;
import cn.wnhyang.coolguard.system.vo.rsa.RsaPageVO;
import cn.wnhyang.coolguard.system.vo.rsa.RsaPairVO;
import cn.wnhyang.coolguard.system.vo.rsa.RsaUpdateVO;

/**
 * 密钥表 服务类
 *
 * @author wnhyang
 * @since 2023/10/10
 */
public interface RsaService {

    /**
     * 生成密钥对
     * 默认生成不能设置补位方法和长度
     *
     * @return 密钥对
     */
    RsaPairVO generateKeyPair();

    /**
     * 新建密钥
     *
     * @param reqVO 密钥
     * @return id
     */
    Long createSecretKey(RsaCreateVO reqVO);

    /**
     * 更新密钥
     *
     * @param reqVO 更新密钥
     */
    void updateRsa(RsaUpdateVO reqVO);

    /**
     * 删除密钥
     *
     * @param id 密钥id
     */
    void deleteRsa(Long id);

    /**
     * 分页密钥
     *
     * @param reqVO 分页
     * @return 密钥集合
     */
    PageResult<RsaDO> getRsaPage(RsaPageVO reqVO);

    /**
     * 详细密钥
     *
     * @param id 密钥id
     * @return 密钥
     */
    RsaDO getRsa(Long id);
}
