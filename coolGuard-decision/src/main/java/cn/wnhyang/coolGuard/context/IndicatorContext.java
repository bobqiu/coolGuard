package cn.wnhyang.coolGuard.context;

import cn.hutool.core.collection.CollUtil;
import cn.wnhyang.coolGuard.entity.IndicatorVersion;
import cn.wnhyang.coolGuard.vo.result.IndicatorResult;
import lombok.Data;

import java.io.Serial;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wnhyang
 * @date 2024/4/3
 **/
public class IndicatorContext {

    private List<IndicatorCtx> indicatorList;

    private final Map<String, IndicatorCtx> indicatorMap = new HashMap<>();

    public void setIndicatorList(List<IndicatorCtx> indicatorList) {
        this.indicatorList = indicatorList;
        indicatorList.forEach(indicator -> indicatorMap.put(indicator.getCode(), indicator));
    }

    public IndicatorCtx getIndicator(int index) {
        return indicatorList.get(index);
    }

    public void setIndicatorValue(int index, Object value) {
        indicatorList.get(index).setValue(String.valueOf(value));
    }

    public String getIndicatorReturnType(String code) {
        return indicatorMap.get(code).getReturnType();
    }

    public Object getIndicatorValue(String code) {
        return indicatorMap.get(code).getValue();
    }

    public List<IndicatorResult> convert() {
        if (CollUtil.isEmpty(indicatorMap)) {
            return null;
        }
        return indicatorMap.values().stream().map(
                indicatorVO -> new IndicatorResult(indicatorVO.getCode(), indicatorVO.getName(), indicatorVO.getType(), indicatorVO.getVersion(), indicatorVO.getValue())).toList();
    }

    @Data
    public static class IndicatorCtx extends IndicatorVersion {

        @Serial
        private static final long serialVersionUID = 1355749887039607327L;

        /**
         * 指标值
         */
        private Object value;

    }
}
