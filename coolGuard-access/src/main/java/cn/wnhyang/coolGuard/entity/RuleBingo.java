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
public class RuleBingo {

    private List<AddTag> addTags;

    private List<AddList> addLists;

    private SendSms sendSms;

    private List<SetField> setFields;

    @Data
    public static class AddTag {
        private String code;
    }

    @Data
    public static class AddList {
        private String listSetCode;

        private String fieldName;
    }

    @Data
    public static class SendSms {
        private String smsTemplateCode;
    }

    @Data
    public static class SetField {
        private String fieldName;

        private String type;

        private String value;
    }
}
