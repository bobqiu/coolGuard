package cn.wnhyang.coolguard.decision.entity;

import cn.wnhyang.coolguard.mybatis.BaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.util.List;
import java.util.Map;

/**
 * 接入表
 *
 * @author wnhyang
 * @since 2024/03/14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "de_access", autoResultMap = true)
public class Access extends BaseDO {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 自增编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 接入名
     */
    @TableField("name")
    private String name;

    /**
     * 接入编码
     */
    @TableField("code")
    private String code;

    /**
     * 输入配置
     */
    @TableField(value = "input_field_list", typeHandler = JacksonTypeHandler.class)
    private List<ParamConfig> inputFieldList;

    /**
     * 输出配置
     */
    @TableField(value = "output_field_list", typeHandler = JacksonTypeHandler.class)
    private List<ParamConfig> outputFieldList;

    /**
     * 测试参数
     */
    @TableField(value = "test_params", typeHandler = JacksonTypeHandler.class)
    private Map<String, String> testParams;

    /**
     * 描述
     */
    @TableField("description")
    private String description;
}
