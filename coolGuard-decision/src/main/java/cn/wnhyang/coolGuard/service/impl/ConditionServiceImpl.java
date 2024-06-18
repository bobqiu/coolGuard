package cn.wnhyang.coolGuard.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.wnhyang.coolGuard.constant.ConditionType;
import cn.wnhyang.coolGuard.constant.ExpectType;
import cn.wnhyang.coolGuard.context.DecisionRequest;
import cn.wnhyang.coolGuard.context.IndicatorContext;
import cn.wnhyang.coolGuard.convert.ConditionConvert;
import cn.wnhyang.coolGuard.entity.Condition;
import cn.wnhyang.coolGuard.enums.FieldType;
import cn.wnhyang.coolGuard.enums.LogicType;
import cn.wnhyang.coolGuard.mapper.ConditionMapper;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.ConditionService;
import cn.wnhyang.coolGuard.util.FunUtil;
import cn.wnhyang.coolGuard.vo.create.ConditionCreateVO;
import cn.wnhyang.coolGuard.vo.page.ConditionPageVO;
import cn.wnhyang.coolGuard.vo.update.ConditionUpdateVO;
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
public class ConditionServiceImpl implements ConditionService {

    private final ConditionMapper conditionMapper;

    @Override
    public Long createCondition(ConditionCreateVO createVO) {
        Condition condition = ConditionConvert.INSTANCE.convert(createVO);
        conditionMapper.insert(condition);
        return condition.getId();
    }

    @Override
    public void updateCondition(ConditionUpdateVO updateVO) {
        Condition condition = ConditionConvert.INSTANCE.convert(updateVO);
        conditionMapper.updateById(condition);
    }

    @Override
    public void deleteCondition(Long id) {
        conditionMapper.deleteById(id);
    }

    @Override
    public Condition getCondition(Long id) {
        return conditionMapper.selectById(id);
    }

    @Override
    public PageResult<Condition> pageCondition(ConditionPageVO pageVO) {
        return conditionMapper.selectPage(pageVO);
    }

    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS_BOOLEAN, nodeId = "cond", nodeType = NodeTypeEnum.BOOLEAN)
    public boolean cond(NodeComponent bindCmp) {

        // 获取当前tag
        String tag = bindCmp.getTag();

        // 获取当前tag对应的条件
        Condition condition = conditionMapper.selectById(tag);

        // 获取上下文
        DecisionRequest decisionRequest = bindCmp.getContextBean(DecisionRequest.class);

        String type = condition.getType();
        boolean cond = false;

        LogicType byType = LogicType.getByType(condition.getLogicType());
        try {
            if (ConditionType.NORMAL.equals(type)) {

                // 获取条件字段
                String fieldName = condition.getValue();
                FieldType fieldType = FieldType.getByFieldName(fieldName);

                String expectValue = condition.getExpectValue();
                if (ExpectType.CONTEXT.equals(condition.getExpectedType())) {
                    expectValue = decisionRequest.getStringData(expectValue);
                }

                if (fieldType == null || byType == null) {
                    return false;
                }

                switch (fieldType) {
                    case STRING:
                        String stringData = decisionRequest.getStringData(fieldName);
                        log.debug("字段值:{}, 操作:{}, 期望值:{}", stringData, byType, expectValue);
                        cond = FunUtil.INSTANCE.stringLogicOp.apply(stringData, byType, expectValue);
                        break;
                    case NUMBER:
                        Integer numberData = decisionRequest.getNumberData(fieldName);
                        Integer expectInteger = Integer.parseInt(expectValue);
                        log.debug("字段值:{}, 操作:{}, 期望值:{}", numberData, byType, expectInteger);
                        cond = FunUtil.INSTANCE.integerLogicOp.apply(numberData, byType, expectInteger);
                        break;
                    case FLOAT:
                        Double floatData = decisionRequest.getFloatData(fieldName);
                        Double expectDouble = Double.parseDouble(expectValue);
                        log.debug("字段值:{}, 操作:{}, 期望值:{}", floatData, byType, expectDouble);
                        cond = FunUtil.INSTANCE.doubleLogicOp.apply(floatData, byType, expectDouble);
                        break;
                    case DATE:
                        LocalDateTime dateData = decisionRequest.getDateData(fieldName);
                        LocalDateTime expectDateTime = LocalDateTimeUtil.parse(expectValue, DatePattern.NORM_DATETIME_FORMATTER);
                        log.debug("字段值:{}, 操作:{}, 期望值:{}", dateData, byType, expectDateTime);
                        cond = FunUtil.INSTANCE.dateLogicOp.apply(dateData, byType, expectDateTime);
                        break;
                    case ENUM:
                        String enumData = decisionRequest.getEnumData(fieldName);
                        log.debug("字段值:{}, 操作:{}, 期望值:{}", enumData, byType, expectValue);
                        cond = FunUtil.INSTANCE.enumLogicOp.apply(enumData, byType, expectValue);
                        break;
                    case BOOLEAN:
                        Boolean booleanData = decisionRequest.getBooleanData(fieldName);
                        log.debug("字段值:{}", booleanData);
                        cond = FunUtil.INSTANCE.booleanLogicOp.apply(booleanData, byType, Boolean.parseBoolean(expectValue));
                        break;
                }

            } else if (ConditionType.ZB.equals(type)) {
                // TODO 指标条件
                log.info("指标条件");
                String indicatorId = condition.getValue();
                IndicatorContext indicatorContext = bindCmp.getContextBean(IndicatorContext.class);
                String indicatorValue = indicatorContext.getIndicatorValue(Long.valueOf(indicatorId));
                cond = FunUtil.INSTANCE.doubleLogicOp.apply(Double.parseDouble(indicatorValue), byType, Double.parseDouble(condition.getExpectValue()));
            } else if (ConditionType.REGULAR.equals(type)) {
                log.info("正则条件");

                String fieldName = condition.getValue();

                String stringData = decisionRequest.getStringData(fieldName);
                cond = FunUtil.INSTANCE.regularLogicOp.apply(stringData, byType, condition.getExpectValue());
            } else if (ConditionType.LIST.equals(type)) {
                // TODO 名单条件
                log.info("名单条件");
                String fieldName = condition.getValue();

                String stringData = decisionRequest.getStringData(fieldName);
                // 查名单集做匹配

            } else if (ConditionType.SCRIPT.equals(type)) {
                // TODO 脚本条件
                log.info("脚本条件");
            } else {
                log.error("未知条件类型:{}", type);
            }
        } catch (Exception e) {
            log.error("条件:{}, 运行异常:{}", condition, e.getMessage());
        }
        return cond;
    }

}
