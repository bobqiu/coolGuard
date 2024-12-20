package cn.wnhyang.coolGuard.context;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.wnhyang.coolGuard.enums.FieldType;
import com.ql.util.express.IExpressContext;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wnhyang
 * @date 2024/12/17
 **/
public class FieldContext extends ConcurrentHashMap<String, Object> implements IExpressContext<String, Object> {

    @Serial
    private static final long serialVersionUID = 9187462074678230424L;

    public void setDataByType(String key, String value, FieldType type) {
        try {
            switch (type) {
                case STRING, ENUM:
                    put(key, value);
                    break;
                case NUMBER:
                    put(key, Integer.parseInt(value));
                    break;
                case FLOAT:
                    put(key, Double.parseDouble(value));
                    break;
                case BOOLEAN:
                    put(key, Boolean.parseBoolean(value));
                    break;
                case DATE:
                    put(key, LocalDateTimeUtil.parse(value, DatePattern.NORM_DATETIME_FORMATTER));
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
        return (String) get(key);
    }

    public Integer getNumberData(String key) {
        return (Integer) get(key);
    }

    public Boolean getBooleanData(String key) {
        return (Boolean) get(key);
    }

    public String getEnumData(String key) {
        return (String) get(key);
    }

    public LocalDateTime getDateData(String key) {
        return (LocalDateTime) get(key);
    }

    public Double getFloatData(String key) {
        return (Double) get(key);
    }


}
