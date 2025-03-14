package cn.wnhyang.coolguard.system.service.impl;

import cn.hutool.crypto.asymmetric.RSA;
import cn.wnhyang.coolguard.common.pojo.PageResult;
import cn.wnhyang.coolguard.system.convert.RsaConvert;
import cn.wnhyang.coolguard.system.entity.RsaDO;
import cn.wnhyang.coolguard.system.mapper.RsaMapper;
import cn.wnhyang.coolguard.system.service.RsaService;
import cn.wnhyang.coolguard.system.vo.rsa.RsaCreateVO;
import cn.wnhyang.coolguard.system.vo.rsa.RsaPageVO;
import cn.wnhyang.coolguard.system.vo.rsa.RsaPairVO;
import cn.wnhyang.coolguard.system.vo.rsa.RsaUpdateVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 密钥表 服务实现类
 *
 * @author wnhyang
 * @since 2023/10/10
 */
@Service
@RequiredArgsConstructor
public class RsaServiceImpl implements RsaService {

    private final RsaMapper rsaMapper;

    @Override
    public RsaPairVO generateKeyPair() {
        RSA rsa = new RSA();
        String publicKey = rsa.getPublicKeyBase64();
        String privateKey = rsa.getPrivateKeyBase64();
        RsaPairVO respVO = new RsaPairVO();
        respVO.setPublicKey(publicKey).setPrivateKey(privateKey);
        return respVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createSecretKey(RsaCreateVO reqVO) {
        RsaDO rsaDO = RsaConvert.INSTANCE.convert(reqVO);
        rsaMapper.insert(rsaDO);
        return rsaDO.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRsa(RsaUpdateVO reqVO) {
        RsaDO rsaDO = RsaConvert.INSTANCE.convert(reqVO);
        rsaMapper.updateById(rsaDO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRsa(Long id) {
        rsaMapper.deleteById(id);
    }

    @Override
    public PageResult<RsaDO> getRsaPage(RsaPageVO reqVO) {
        return rsaMapper.selectPage(reqVO, null);
    }

    @Override
    public RsaDO getRsa(Long id) {
        return rsaMapper.selectById(id);
    }
}
