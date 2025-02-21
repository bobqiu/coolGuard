package cn.wnhyang.coolGuard.analysis.pn;

import cn.hutool.core.util.StrUtil;
import cn.wnhyang.coolGuard.constant.FieldCode;
import cn.wnhyang.coolGuard.context.FieldContext;
import cn.wnhyang.coolGuard.enums.FieldType;

/**
 * @author wnhyang
 * @date 2024/5/31
 **/
public interface PhoneNoAnalysis {

    default void init() {

    }

    PhoneNoInfo analysis(String phoneNumber);

    default void parsePhoneNumber(FieldContext fieldContext) {
        String phoneNumber = fieldContext.getData(FieldCode.PAYER_PHONE_NUMBER, String.class);
        if (StrUtil.isNotBlank(phoneNumber)) {
            PhoneNoInfo phoneNoInfo = analysis(phoneNumber);
            fieldContext.setDataByType(FieldCode.PHONE_NUMBER_PROVINCE, phoneNoInfo.getProvince(), FieldType.STRING);
            fieldContext.setDataByType(FieldCode.PHONE_NUMBER_CITY, phoneNoInfo.getCity(), FieldType.STRING);
            fieldContext.setDataByType(FieldCode.PHONE_NUMBER_ISP, phoneNoInfo.getIsp(), FieldType.STRING);
        }

    }
}
