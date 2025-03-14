package cn.wnhyang.coolguard.decision.analysis.idcard;

import cn.hutool.core.util.IdcardUtil;
import cn.hutool.core.util.StrUtil;
import cn.wnhyang.coolguard.decision.analysis.ad.Pca;
import cn.wnhyang.coolguard.decision.constant.FieldCode;
import cn.wnhyang.coolguard.decision.context.FieldContext;
import cn.wnhyang.coolguard.decision.enums.FieldType;
import cn.wnhyang.coolguard.decision.util.AdocUtil;

/**
 * @author wnhyang
 * @date 2025/2/20
 **/
public interface IdCardAnalysis {
    default void init() {

    }

    default void parseIdCard(FieldContext fieldContext) {
        String idCard = fieldContext.getData(FieldCode.PAYER_ID_NUMBER, String.class);
        if (StrUtil.isNotBlank(idCard) && IdcardUtil.isValidCard(idCard)) {
            Pca pca = AdocUtil.getPca(IdcardUtil.getDistrictCodeByIdCard(idCard));
            if (pca != null) {
                fieldContext.setDataByType(FieldCode.ID_CARD_PROVINCE, pca.getProvince(), FieldType.STRING);
                fieldContext.setDataByType(FieldCode.ID_CARD_CITY, pca.getCity(), FieldType.STRING);
                fieldContext.setDataByType(FieldCode.ID_CARD_DISTRICT, pca.getArea(), FieldType.STRING);
            }
        }
    }
}
