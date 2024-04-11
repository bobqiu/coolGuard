package cn.wnhyang.coolGuard.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.StrUtil;
import cn.wnhyang.coolGuard.context.DecisionRequest;
import cn.wnhyang.coolGuard.convert.RuleConditionConvert;
import cn.wnhyang.coolGuard.entity.RuleCondition;
import cn.wnhyang.coolGuard.enums.FieldType;
import cn.wnhyang.coolGuard.enums.OperateType;
import cn.wnhyang.coolGuard.mapper.RuleConditionMapper;
import cn.wnhyang.coolGuard.pojo.PageResult;
import cn.wnhyang.coolGuard.service.RuleConditionService;
import cn.wnhyang.coolGuard.vo.create.RuleConditionCreateVO;
import cn.wnhyang.coolGuard.vo.page.RuleConditionPageVO;
import cn.wnhyang.coolGuard.vo.update.RuleConditionUpdateVO;
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
@RequiredArgsConstructor
public class RuleConditionServiceImpl implements RuleConditionService {

    private final RuleConditionMapper ruleConditionMapper;

    @Override
    public Long createRuleCondition(RuleConditionCreateVO createVO) {
        RuleCondition ruleCondition = RuleConditionConvert.INSTANCE.convert(createVO);
        ruleConditionMapper.insert(ruleCondition);
        return ruleCondition.getId();
    }

    @Override
    public void updateRuleCondition(RuleConditionUpdateVO updateVO) {
        RuleCondition ruleCondition = RuleConditionConvert.INSTANCE.convert(updateVO);
        ruleConditionMapper.updateById(ruleCondition);
    }

    @Override
    public void deleteRuleCondition(Long id) {
        ruleConditionMapper.deleteById(id);
    }

    @Override
    public RuleCondition getRuleCondition(Long id) {
        return ruleConditionMapper.selectById(id);
    }

    @Override
    public PageResult<RuleCondition> pageRuleCondition(RuleConditionPageVO pageVO) {
        return ruleConditionMapper.selectPage(pageVO);
    }

    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS_IF, nodeId = "ruleConditionIf", nodeType = NodeTypeEnum.IF)
    public boolean ruleConditionIf(NodeComponent bindCmp) {

        // 获取当前tag
        String tag = bindCmp.getTag();

        // 获取当前tag对应的条件
        RuleCondition ruleCondition = ruleConditionMapper.selectById(tag);

        // 获取上下文
        DecisionRequest decisionRequest = bindCmp.getContextBean(DecisionRequest.class);

        // 获取条件字段
        String fieldName = ruleCondition.getFieldName();
        FieldType fieldType = FieldType.getByFieldName(fieldName);


        OperateType byType = OperateType.getByType(ruleCondition.getOperateType());

        // TODO 当前是常量，之后要考虑变量

        String expectValue = ruleCondition.getExpectValue();

        if (fieldType == null || byType == null) {
            return false;
        }

        boolean condition = false;

        // 获取字段值
        // TODO 支持String、Integer、Double、Boolean等
        try {
            switch (fieldType) {
                case STRING:
                    String stringData = decisionRequest.getStringData(fieldName);
                    log.info("字段值:{}, 操作:{}, 期望值:{}", stringData, byType, expectValue);
                    condition = switch (Objects.requireNonNull(byType)) {
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
                    log.info("字段值:{}, 操作:{}, 期望值:{}", numberData, byType, expectInteger);
                    condition = switch (Objects.requireNonNull(byType)) {
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
                    log.info("字段值:{}, 操作:{}, 期望值:{}", floatData, byType, expectDouble);
                    condition = switch (Objects.requireNonNull(byType)) {
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
                    log.info("字段值:{}, 操作:{}, 期望值:{}", dateData, byType, expectDateTime);
                    condition = switch (Objects.requireNonNull(byType)) {
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
                    log.info("字段值:{}, 操作:{}, 期望值:{}", enumData, byType, expectValue);
                    condition = switch (Objects.requireNonNull(byType)) {
                        case NULL -> enumData == null;
                        case NOT_NULL -> enumData != null;
                        case EQ -> enumData.equals(expectValue);
                        case NOT_EQ -> !enumData.equals(expectValue);
                        default -> false;
                    };
                    break;
                case BOOLEAN:
                    Boolean booleanData = decisionRequest.getBooleanData(fieldName);
                    log.info("字段值:{}", booleanData);
                    condition = switch (Objects.requireNonNull(byType)) {
                        case NULL -> booleanData == null;
                        case NOT_NULL -> booleanData != null;
                        case EQ -> booleanData.equals(Boolean.parseBoolean(expectValue));
                        case NOT_EQ -> !booleanData.equals(Boolean.parseBoolean(expectValue));
                        default -> false;
                    };
                    break;
            }
        } catch (Exception e) {
            log.info("规则条件运行异常:{}", e.getMessage());
        }

        return condition;
    }

}
