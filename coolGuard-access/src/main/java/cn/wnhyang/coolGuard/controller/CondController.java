package cn.wnhyang.coolGuard.controller;

import cn.wnhyang.coolGuard.enums.FieldType;
import cn.wnhyang.coolGuard.enums.LogicType;
import cn.wnhyang.coolGuard.service.CondService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 条件
 *
 * @author wnhyang
 * @date 2024/7/29
 **/
@Slf4j
@RestController
@RequestMapping("/cond")
@RequiredArgsConstructor
public class CondController {

    private final CondService condService;


    /**
     * 获取字段类型列表
     *
     * @return 字段类型列表
     */
    @GetMapping("/fieldType")
    public List<String> fieldType() {
        return FieldType.getTypeList();
    }

    /**
     * 根据字段类型获取逻辑运算符列表
     *
     * @param fieldType 字段类型
     * @return 运算符列表
     */
    @GetMapping("/logicOpByFieldType")
    public List<String> logicOpByFieldType(String fieldType) {
        return LogicType.getTypeList(fieldType);
    }
}
