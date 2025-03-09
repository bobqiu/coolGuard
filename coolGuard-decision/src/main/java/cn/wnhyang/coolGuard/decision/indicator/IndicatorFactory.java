package cn.wnhyang.coolGuard.decision.indicator;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wnhyang
 * @date 2025/3/8
 **/
@Component
public class IndicatorFactory {

    private final Map<String, AbstractIndicator> INDICATOR_MAP = new HashMap<>(32);

    public IndicatorFactory(List<AbstractIndicator> indicatorList) {
        indicatorList.forEach(indicator -> INDICATOR_MAP.put(indicator.getTypeCode(), indicator));
    }

    public AbstractIndicator getIndicator(String typeCode) {
        return INDICATOR_MAP.get(typeCode);
    }
}
