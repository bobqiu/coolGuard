package cn.wnhyang.coolGuard.vo;

import cn.wnhyang.coolGuard.vo.base.AccessBaseVO;
import lombok.Data;

import java.io.Serial;
import java.util.List;
import java.util.Map;

/**
 * @author wnhyang
 * @date 2024/3/14
 **/
@Data
public class AccessVO extends AccessBaseVO {

    @Serial
    private static final long serialVersionUID = -6121337194207258432L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 接入编码
     */
    private String code;

    /**
     * 测试参数
     */
    private Map<String, String> testParams;

    /**
     * 输入字段
     */
    private List<InputFieldVO> inputFieldList;

    /**
     * 输出字段
     */
    private List<OutputFieldVO> outputFieldList;
}
