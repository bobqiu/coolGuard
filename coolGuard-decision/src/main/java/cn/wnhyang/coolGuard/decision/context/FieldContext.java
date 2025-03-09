package cn.wnhyang.coolGuard.decision.context;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.wnhyang.coolGuard.decision.enums.FieldType;
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

    public <T> T getData(String key, Class<T> clazz) {
        return clazz.cast(get(key));
    }

    public String getData2String(String key) {
        if (get(key) == null) {
            return null;
        }
        if (get(key) instanceof LocalDateTime) {
            return LocalDateTimeUtil.formatNormal(getData(key, LocalDateTime.class));
        }
        return get(key).toString();
    }

}
