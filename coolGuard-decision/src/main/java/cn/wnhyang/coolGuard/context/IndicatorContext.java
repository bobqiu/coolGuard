package cn.wnhyang.coolGuard.context;

import cn.wnhyang.coolGuard.vo.IndicatorVO;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wnhyang
 * @date 2024/4/3
 **/
public class IndicatorContext {

    private final Map<Long, IndicatorVO> indicatorMap = new ConcurrentHashMap<>();

    public void addIndicator(Long id, IndicatorVO indicatorVO) {
        indicatorMap.put(id, indicatorVO);
    }

    public IndicatorVO getIndicator(Long id) {
        return indicatorMap.get(id);
    }

    public boolean hasIndicator(Long id) {
        return indicatorMap.containsKey(id);
    }

    public void setIndicatorValue(Long id, double value) {
        indicatorMap.get(id).setValue(String.valueOf(value));
    }

    public String getIndicatorValue(Long id) {
        return indicatorMap.get(id).getValue();
    }

}
