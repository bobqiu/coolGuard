package cn.wnhyang.coolGuard.analysis.idcard;

import cn.hutool.core.util.IdcardUtil;
import cn.hutool.core.util.StrUtil;
import cn.wnhyang.coolGuard.analysis.ad.Pca;
import cn.wnhyang.coolGuard.constant.FieldCode;
import cn.wnhyang.coolGuard.context.FieldContext;
import cn.wnhyang.coolGuard.enums.FieldType;
import cn.wnhyang.coolGuard.util.AdocUtil;

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
