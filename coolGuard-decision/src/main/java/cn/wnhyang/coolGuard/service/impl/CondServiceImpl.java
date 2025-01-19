package cn.wnhyang.coolGuard.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.wnhyang.coolGuard.constant.ValueType;
import cn.wnhyang.coolGuard.context.FieldContext;
import cn.wnhyang.coolGuard.context.IndicatorContext;
import cn.wnhyang.coolGuard.entity.Cond;
import cn.wnhyang.coolGuard.enums.CondType;
import cn.wnhyang.coolGuard.enums.FieldType;
import cn.wnhyang.coolGuard.enums.LogicType;
import cn.wnhyang.coolGuard.service.CondService;
import cn.wnhyang.coolGuard.service.ListDataService;
import cn.wnhyang.coolGuard.util.FunUtil;
import cn.wnhyang.coolGuard.util.LFUtil;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.annotation.LiteflowMethod;
import com.yomahub.liteflow.core.NodeComponent;
import com.yomahub.liteflow.enums.LiteFlowMethodEnum;
import com.yomahub.liteflow.enums.NodeTypeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 规则条件表 服务实现类
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@Slf4j
@Service
@LiteflowComponent
@RequiredArgsConstructor
public class CondServiceImpl implements CondService {

    private final ListDataService listDataService;

    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS_BOOLEAN, nodeId = LFUtil.COND, nodeType = NodeTypeEnum.BOOLEAN, nodeName = "条件组件")
    public boolean cond(NodeComponent bindCmp) {
        Cond cond = bindCmp.getCmpData(Cond.class);
        if (cond == null) {
            return true;
        }
        // 如果有子节点，则递归计算子节点的结果
        if (cond.getChildren() != null && !cond.getChildren().isEmpty()) {
            List<Cond> children = cond.getChildren();
            return switch (cond.getRelation().toUpperCase()) {
                case "AND" -> children.stream().allMatch((item) -> condLeaf(bindCmp, item));
                case "OR" -> children.stream().anyMatch((item) -> condLeaf(bindCmp, item));
                default -> throw new IllegalArgumentException("Unsupported relation: " + cond.getRelation());
            };
        }

        // 处理叶子节点
        return condLeaf(bindCmp, cond);
    }

    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS_BOOLEAN, nodeId = LFUtil.COND_LEAF, nodeType = NodeTypeEnum.BOOLEAN, nodeName = "条件叶子组件")
    public boolean condLeaf(NodeComponent bindCmp, Cond cond) {
        if (cond == null) {
            return false;
        }

        // 获取上下文
        FieldContext fieldContext = bindCmp.getContextBean(FieldContext.class);

        boolean b = false;

        try {
            CondType condType = CondType.getByType(cond.getType());
            LogicType byType = LogicType.getByType(cond.getLogicType());
            switch (condType) {
                // 普通条件，适用指标、规则
                case NORMAL -> {
                    // 获取条件字段
                    String fieldName = cond.getLeftValue();
                    FieldType fieldType = FieldType.getByFieldName(fieldName);

                    String expectValue = cond.getRightValue();
                    if (ValueType.CONTEXT.equals(cond.getRightType())) {
                        expectValue = fieldContext.getStringData(expectValue);
                    }

                    if (fieldType == null || byType == null) {
                        return false;
                    }

                    switch (fieldType) {
                        case STRING:
                            String stringData = fieldContext.getStringData(fieldName);
                            log.debug("字段值:{}, 操作:{}, 期望值:{}", stringData, byType, expectValue);
                            b = FunUtil.INSTANCE.stringLogicOp.apply(stringData, byType, expectValue);
                            break;
                        case NUMBER:
                            Integer numberData = fieldContext.getNumberData(fieldName);
                            Integer expectInteger = Integer.parseInt(expectValue);
                            log.debug("字段值:{}, 操作:{}, 期望值:{}", numberData, byType, expectInteger);
                            b = FunUtil.INSTANCE.integerLogicOp.apply(numberData, byType, expectInteger);
                            break;
                        case FLOAT:
                            Double floatData = fieldContext.getFloatData(fieldName);
                            Double expectDouble = Double.parseDouble(expectValue);
                            log.debug("字段值:{}, 操作:{}, 期望值:{}", floatData, byType, expectDouble);
                            b = FunUtil.INSTANCE.doubleLogicOp.apply(floatData, byType, expectDouble);
                            break;
                        case DATE:
                            LocalDateTime dateData = fieldContext.getDateData(fieldName);
                            LocalDateTime expectDateTime = LocalDateTimeUtil.parse(expectValue, DatePattern.NORM_DATETIME_FORMATTER);
                            log.debug("字段值:{}, 操作:{}, 期望值:{}", dateData, byType, expectDateTime);
                            b = FunUtil.INSTANCE.dateLogicOp.apply(dateData, byType, expectDateTime);
                            break;
                        case ENUM:
                            String enumData = fieldContext.getEnumData(fieldName);
                            log.debug("字段值:{}, 操作:{}, 期望值:{}", enumData, byType, expectValue);
                            b = FunUtil.INSTANCE.enumLogicOp.apply(enumData, byType, expectValue);
                            break;
                        case BOOLEAN:
                            Boolean booleanData = fieldContext.getBooleanData(fieldName);
                            log.debug("字段值:{}", booleanData);
                            b = FunUtil.INSTANCE.booleanLogicOp.apply(booleanData, byType, Boolean.parseBoolean(expectValue));
                            break;
                    }
                }
                case ZB -> {
                    log.info("指标条件");
                    String indicatorCode = cond.getLeftValue();
                    IndicatorContext indicatorContext = bindCmp.getContextBean(IndicatorContext.class);
                    String iType = indicatorContext.getIndicatorReturnType(indicatorCode);
                    FieldType fieldType = FieldType.getByType(iType);

                    String expectValue = cond.getRightValue();
                    if (ValueType.CONTEXT.equals(cond.getRightType())) {
                        expectValue = fieldContext.getStringData(expectValue);
                    }

                    if (fieldType == null || byType == null) {
                        return false;
                    }

                    switch (fieldType) {
                        case STRING:
                            String stringData = (String) indicatorContext.getIndicatorValue(indicatorCode);
                            log.debug("字段值:{}, 操作:{}, 期望值:{}", stringData, byType, expectValue);
                            b = FunUtil.INSTANCE.stringLogicOp.apply(stringData, byType, expectValue);
                            break;
                        case NUMBER:
                            Integer numberData = (Integer) indicatorContext.getIndicatorValue(indicatorCode);
                            Integer expectInteger = Integer.parseInt(expectValue);
                            log.debug("字段值:{}, 操作:{}, 期望值:{}", numberData, byType, expectInteger);
                            b = FunUtil.INSTANCE.integerLogicOp.apply(numberData, byType, expectInteger);
                            break;
                        case FLOAT:
                            Double floatData = (Double) indicatorContext.getIndicatorValue(indicatorCode);
                            Double expectDouble = Double.parseDouble(expectValue);
                            log.debug("字段值:{}, 操作:{}, 期望值:{}", floatData, byType, expectDouble);
                            b = FunUtil.INSTANCE.doubleLogicOp.apply(floatData, byType, expectDouble);
                            break;
                        case DATE:
                            LocalDateTime dateData = (LocalDateTime) indicatorContext.getIndicatorValue(indicatorCode);
                            LocalDateTime expectDateTime = LocalDateTimeUtil.parse(expectValue, DatePattern.NORM_DATETIME_FORMATTER);
                            log.debug("字段值:{}, 操作:{}, 期望值:{}", dateData, byType, expectDateTime);
                            b = FunUtil.INSTANCE.dateLogicOp.apply(dateData, byType, expectDateTime);
                            break;
                        case ENUM:
                            String enumData = (String) indicatorContext.getIndicatorValue(indicatorCode);
                            log.debug("字段值:{}, 操作:{}, 期望值:{}", enumData, byType, expectValue);
                            b = FunUtil.INSTANCE.enumLogicOp.apply(enumData, byType, expectValue);
                            break;
                        case BOOLEAN:
                            Boolean booleanData = (Boolean) indicatorContext.getIndicatorValue(indicatorCode);
                            log.debug("字段值:{}", booleanData);
                            b = FunUtil.INSTANCE.booleanLogicOp.apply(booleanData, byType, Boolean.parseBoolean(expectValue));
                            break;
                    }
                }
                case REGULAR -> {
                    log.info("正则条件");

                    String fieldName = cond.getLeftValue();

                    String stringData = fieldContext.getStringData(fieldName);
                    b = FunUtil.INSTANCE.regularLogicOp.apply(stringData, byType, cond.getRightValue());
                }
                case LIST -> {
                    log.info("名单条件");
                    String fieldName = cond.getLeftValue();

                    String stringData = fieldContext.getStringData(fieldName);
                    // 查名单集做匹配
                    b = listDataService.hasListData(cond.getRightValue(), stringData);
                }
                case SCRIPT -> {
                    // TODO 脚本条件
                    log.info("脚本条件");
                }
                default -> {
                    log.error("未知条件类型:{}", condType);
                }
            }
        } catch (Exception e) {
            log.error("条件:{}, 运行异常:{}", cond, e.getMessage());
        }
        return b;
    }

}
