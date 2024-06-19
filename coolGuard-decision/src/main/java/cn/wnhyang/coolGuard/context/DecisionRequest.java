package cn.wnhyang.coolGuard.context;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.wnhyang.coolGuard.entity.Access;
import cn.wnhyang.coolGuard.enums.FieldType;
import cn.wnhyang.coolGuard.vo.InputFieldVO;
import cn.wnhyang.coolGuard.vo.OutputFieldVO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wnhyang
 * @date 2024/4/8
 **/
@Getter
@AllArgsConstructor
public class DecisionRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 8326347921307896577L;

    private final String accessName;

    private final Map<String, String> params;

    private final Access access;

    private final List<InputFieldVO> inputFields;

    private final List<OutputFieldVO> outputFields;

    private final Map<String, Object> fields = new ConcurrentHashMap<>();

    public void setDataByType(String key, String value, FieldType type) {
        try {
            switch (type) {
                case STRING, ENUM:
                    fields.put(key, value);
                    break;
                case NUMBER:
                    fields.put(key, Integer.parseInt(value));
                    break;
                case FLOAT:
                    fields.put(key, Double.parseDouble(value));
                    break;
                case BOOLEAN:
                    fields.put(key, Boolean.parseBoolean(value));
                    break;
                case DATE:
                    fields.put(key, LocalDateTimeUtil.parse(value, DatePattern.NORM_DATETIME_FORMATTER));
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported field type: " + type);
            }
        } catch (NumberFormatException e) {
            // 处理解析异常，例如记录日志或抛出自定义异常
            throw new IllegalArgumentException("Invalid value for type " + type + ": " + value, e);
        }
    }

    public String getStringData(String key) {
        return (String) fields.get(key);
    }

    public Integer getNumberData(String key) {
        return (Integer) fields.get(key);
    }

    public Boolean getBooleanData(String key) {
        return (Boolean) fields.get(key);
    }

    public String getEnumData(String key) {
        return (String) fields.get(key);
    }

    public LocalDateTime getDateData(String key) {
        return (LocalDateTime) fields.get(key);
    }

    public Double getFloatData(String key) {
        return (Double) fields.get(key);
    }


}
