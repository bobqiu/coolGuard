package cn.wnhyang.coolGuard.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author wnhyang
 * @date 2024/12/8
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Action {

    private List<AddTag> addTag;

    private List<AddList> addList;

    private SendSms sendSms;

    private List<SetField> setField;

    @Data
    public static class AddTag {
        private String code;
    }

    @Data
    public static class AddList {
        private String listSetCode;

        private String fieldCode;
    }

    @Data
    public static class SendSms {
        private String smsTemplateCode;
    }

    @Data
    public static class SetField {
        private String fieldCode;

        private String type;

        private String value;
    }
}
