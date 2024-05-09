package cn.wnhyang.coolGuard.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.StrUtil;
import cn.wnhyang.coolGuard.constant.ExpectType;
import cn.wnhyang.coolGuard.context.DecisionRequest;
import cn.wnhyang.coolGuard.convert.ConditionConvert;
import cn.wnhyang.coolGuard.entity.Condition;
import cn.wnhyang.coolGuard.enums.FieldType;
import cn.wnhyang.coolGuard.enums.OperateType;
import cn.wnhyang.coolGuard.mapper.ConditionMapper;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.ConditionService;
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
import java.util.Objects;

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

        // 获取条件字段
        String fieldName = condition.getFieldName();
        FieldType fieldType = FieldType.getByFieldName(fieldName);


        OperateType byType = OperateType.getByType(condition.getOperateType());

        String expectValue = condition.getExpectValue();
        if (ExpectType.CONTEXT.equals(condition.getExpectedType())) {
            expectValue = decisionRequest.getStringData(expectValue);
        }

        if (fieldType == null || byType == null) {
            return false;
        }

        boolean cond = false;

        // 获取字段值
        try {
            switch (fieldType) {
                case STRING:
                    String stringData = decisionRequest.getStringData(fieldName);
                    log.debug("字段值:{}, 操作:{}, 期望值:{}", stringData, byType, expectValue);
                    cond = switch (Objects.requireNonNull(byType)) {
                        case NULL -> StrUtil.isBlank(stringData);
                        case NOT_NULL -> !StrUtil.isBlank(stringData);
                        case EQ -> stringData.equals(expectValue);
                        case NOT_EQ -> !stringData.equals(expectValue);
                        case CONTAINS -> stringData.contains(expectValue);
                        case NOT_CONTAINS -> !stringData.contains(expectValue);
                        case PREFIX -> stringData.startsWith(expectValue);
                        case NOT_PREFIX -> !stringData.startsWith(expectValue);
                        case SUFFIX -> stringData.endsWith(expectValue);
                        case NOT_SUFFIX -> !stringData.endsWith(expectValue);
                        default -> false;
                    };
                    break;
                case NUMBER:
                    Integer numberData = decisionRequest.getNumberData(fieldName);
                    Integer expectInteger = Integer.parseInt(expectValue);
                    log.debug("字段值:{}, 操作:{}, 期望值:{}", numberData, byType, expectInteger);
                    cond = switch (Objects.requireNonNull(byType)) {
                        case NULL -> numberData == null;
                        case NOT_NULL -> numberData != null;
                        case EQ -> numberData.equals(expectInteger);
                        case NOT_EQ -> !numberData.equals(expectInteger);
                        case LT -> numberData < expectInteger;
                        case LTE -> numberData <= expectInteger;
                        case GT -> numberData > expectInteger;
                        case GTE -> numberData >= expectInteger;
                        default -> false;
                    };
                    break;
                case FLOAT:
                    Double floatData = decisionRequest.getFloatData(fieldName);
                    Double expectDouble = Double.parseDouble(expectValue);
                    log.debug("字段值:{}, 操作:{}, 期望值:{}", floatData, byType, expectDouble);
                    cond = switch (Objects.requireNonNull(byType)) {
                        case NULL -> floatData == null;
                        case NOT_NULL -> floatData != null;
                        case EQ -> floatData.equals(expectDouble);
                        case NOT_EQ -> !floatData.equals(expectDouble);
                        case LT -> floatData < expectDouble;
                        case LTE -> floatData <= expectDouble;
                        case GT -> floatData > expectDouble;
                        case GTE -> floatData >= expectDouble;
                        default -> false;
                    };
                    break;
                case DATE:
                    LocalDateTime dateData = decisionRequest.getDateData(fieldName);
                    LocalDateTime expectDateTime = LocalDateTimeUtil.parse(expectValue, DatePattern.NORM_DATETIME_FORMATTER);
                    log.debug("字段值:{}, 操作:{}, 期望值:{}", dateData, byType, expectDateTime);
                    cond = switch (Objects.requireNonNull(byType)) {
                        case NULL -> dateData == null;
                        case NOT_NULL -> dateData != null;
                        case EQ -> dateData.equals(expectDateTime);
                        case NOT_EQ -> !dateData.equals(expectDateTime);
                        case LT -> dateData.isBefore(expectDateTime);
                        case LTE -> dateData.isBefore(expectDateTime) || dateData.equals(expectDateTime);
                        case GT -> dateData.isAfter(expectDateTime);
                        case GTE -> dateData.isAfter(expectDateTime) || dateData.equals(expectDateTime);
                        default -> false;
                    };
                    break;
                case ENUM:
                    String enumData = decisionRequest.getEnumData(fieldName);
                    log.debug("字段值:{}, 操作:{}, 期望值:{}", enumData, byType, expectValue);
                    cond = switch (Objects.requireNonNull(byType)) {
                        case NULL -> enumData == null;
                        case NOT_NULL -> enumData != null;
                        case EQ -> enumData.equals(expectValue);
                        case NOT_EQ -> !enumData.equals(expectValue);
                        default -> false;
                    };
                    break;
                case BOOLEAN:
                    Boolean booleanData = decisionRequest.getBooleanData(fieldName);
                    log.debug("字段值:{}", booleanData);
                    cond = switch (Objects.requireNonNull(byType)) {
                        case NULL -> booleanData == null;
                        case NOT_NULL -> booleanData != null;
                        case EQ -> booleanData.equals(Boolean.parseBoolean(expectValue));
                        case NOT_EQ -> !booleanData.equals(Boolean.parseBoolean(expectValue));
                        default -> false;
                    };
                    break;
            }
        } catch (Exception e) {
            log.error("条件运行异常:{}", e.getMessage());
        }

        return cond;
    }

}
