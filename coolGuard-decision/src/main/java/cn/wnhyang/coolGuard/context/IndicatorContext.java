package cn.wnhyang.coolGuard.context;

import cn.hutool.core.collection.CollUtil;
import cn.wnhyang.coolGuard.entity.Cond;
import cn.wnhyang.coolGuard.vo.result.IndicatorResult;
import lombok.Data;

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
    public static class IndicatorCtx {

        /**
         * 主键
         */
        private Long id;

        /**
         * 编码
         */
        private String code;

        /**
         * 类型
         */
        private String type;

        /**
         * 计算字段
         */
        private String calcField;

        /**
         * 返回类型
         */
        private String returnType;

        /**
         * earliest latest
         * 返回取值方式
         */
        private String returnFlag;

        /**
         * 窗口大小
         */
        private String winSize;

        /**
         * 窗口类型
         */
        private String winType;

        /**
         * 窗口数量
         */
        private Integer winCount;

        /**
         * 主字段
         */
        private String masterField;

        /**
         * 从字段
         */
        private List<String> slaveFields;

        /**
         * 过滤脚本
         */
        private String filterScript;

        /**
         * 是否发布
         */
        private Boolean publish;

        /**
         * 版本号
         */
        private Integer version;

        /**
         * 场景
         */
        private List<String> scenes;

        /**
         * 场景类型
         */
        private String sceneType;

        /**
         * 时间片
         */
        private Long timeSlice;

        /**
         * 指标值
         */
        private Object value;

        /**
         * 指标名
         */
        private String name;

        /**
         * 描述
         */
        private String description;

        /**
         * 条件
         */
        private Cond cond;
    }
}
