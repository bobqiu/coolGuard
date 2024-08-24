package cn.wnhyang.coolGuard.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.wnhyang.coolGuard.constant.CondType;
import cn.wnhyang.coolGuard.constant.ExpectType;
import cn.wnhyang.coolGuard.context.AccessRequest;
import cn.wnhyang.coolGuard.context.IndicatorContext;
import cn.wnhyang.coolGuard.enums.FieldType;
import cn.wnhyang.coolGuard.enums.LogicType;
import cn.wnhyang.coolGuard.service.CondService;
import cn.wnhyang.coolGuard.service.ListDataService;
import cn.wnhyang.coolGuard.util.FunUtils;
import cn.wnhyang.coolGuard.util.LFUtil;
import cn.wnhyang.coolGuard.vo.Cond;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.annotation.LiteflowMethod;
import com.yomahub.liteflow.core.NodeComponent;
import com.yomahub.liteflow.enums.LiteFlowMethodEnum;
import com.yomahub.liteflow.enums.NodeTypeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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

    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS_BOOLEAN, nodeId = LFUtil.CONDITION_COMMON_NODE, nodeType = NodeTypeEnum.BOOLEAN, nodeName = "条件组件")
    public boolean cond(NodeComponent bindCmp) {
        Cond cond = bindCmp.getCmpData(Cond.class);

        // 获取上下文
        AccessRequest accessRequest = bindCmp.getContextBean(AccessRequest.class);

        String type = cond.getType();
        boolean b = false;

        LogicType byType = LogicType.getByType(cond.getLogicType());
        try {
            // 普通条件，适用指标、规则
            if (CondType.NORMAL.equals(type)) {

                // 获取条件字段
                String fieldName = cond.getValue();
                FieldType fieldType = FieldType.getByFieldName(fieldName);

                String expectValue = cond.getExpectValue();
                if (ExpectType.CONTEXT.equals(cond.getExpectType())) {
                    expectValue = accessRequest.getStringData(expectValue);
                }

                if (fieldType == null || byType == null) {
                    return false;
                }

                switch (fieldType) {
                    case STRING:
                        String stringData = accessRequest.getStringData(fieldName);
                        log.debug("字段值:{}, 操作:{}, 期望值:{}", stringData, byType, expectValue);
                        b = FunUtils.INSTANCE.stringLogicOp.apply(stringData, byType, expectValue);
                        break;
                    case NUMBER:
                        Integer numberData = accessRequest.getNumberData(fieldName);
                        Integer expectInteger = Integer.parseInt(expectValue);
                        log.debug("字段值:{}, 操作:{}, 期望值:{}", numberData, byType, expectInteger);
                        b = FunUtils.INSTANCE.integerLogicOp.apply(numberData, byType, expectInteger);
                        break;
                    case FLOAT:
                        Double floatData = accessRequest.getFloatData(fieldName);
                        Double expectDouble = Double.parseDouble(expectValue);
                        log.debug("字段值:{}, 操作:{}, 期望值:{}", floatData, byType, expectDouble);
                        b = FunUtils.INSTANCE.doubleLogicOp.apply(floatData, byType, expectDouble);
                        break;
                    case DATE:
                        LocalDateTime dateData = accessRequest.getDateData(fieldName);
                        LocalDateTime expectDateTime = LocalDateTimeUtil.parse(expectValue, DatePattern.NORM_DATETIME_FORMATTER);
                        log.debug("字段值:{}, 操作:{}, 期望值:{}", dateData, byType, expectDateTime);
                        b = FunUtils.INSTANCE.dateLogicOp.apply(dateData, byType, expectDateTime);
                        break;
                    case ENUM:
                        String enumData = accessRequest.getEnumData(fieldName);
                        log.debug("字段值:{}, 操作:{}, 期望值:{}", enumData, byType, expectValue);
                        b = FunUtils.INSTANCE.enumLogicOp.apply(enumData, byType, expectValue);
                        break;
                    case BOOLEAN:
                        Boolean booleanData = accessRequest.getBooleanData(fieldName);
                        log.debug("字段值:{}", booleanData);
                        b = FunUtils.INSTANCE.booleanLogicOp.apply(booleanData, byType, Boolean.parseBoolean(expectValue));
                        break;
                }

            } else if (CondType.ZB.equals(type)) {
                log.info("指标条件");
                String indicatorId = cond.getValue();
                IndicatorContext indicatorContext = bindCmp.getContextBean(IndicatorContext.class);
                String indicatorValue = indicatorContext.getIndicatorValue(Long.parseLong(indicatorId));
                String expectValue = cond.getExpectValue();
                if (ExpectType.CONTEXT.equals(cond.getExpectType())) {
                    expectValue = accessRequest.getStringData(expectValue);
                }
                b = FunUtils.INSTANCE.doubleLogicOp.apply(Double.parseDouble(indicatorValue), byType, Double.valueOf(expectValue));
            } else if (CondType.REGULAR.equals(type)) {
                log.info("正则条件");

                String fieldName = cond.getValue();

                String stringData = accessRequest.getStringData(fieldName);
                b = FunUtils.INSTANCE.regularLogicOp.apply(stringData, byType, cond.getExpectValue());
            } else if (CondType.LIST.equals(type)) {
                log.info("名单条件");
                String fieldName = cond.getValue();

                String stringData = accessRequest.getStringData(fieldName);
                // 查名单集做匹配
                b = listDataService.hasListData(Long.valueOf(cond.getExpectValue()), stringData);
            } else if (CondType.SCRIPT.equals(type)) {
                // TODO 脚本条件
                log.info("脚本条件");
            } else {
                log.error("未知条件类型:{}", type);
            }
        } catch (Exception e) {
            log.error("条件:{}, 运行异常:{}", cond, e.getMessage());
        }
        return b;
    }

}
