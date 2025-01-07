package cn.wnhyang.coolGuard.entity;

import cn.wnhyang.coolGuard.pojo.BaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serial;

/**
 * chain表
 *
 * @author wnhyang
 * @since 2024/04/04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("de_chain")
public class Chain extends BaseDO {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 应用名
     */
    @TableField("application_name")
    private String applicationName;

    /**
     * chain名
     */
    @TableField("chain_name")
    private String chainName;

    /**
     * el数据
     */
    @TableField("el_data")
    private String elData;

    /**
     * chain状态
     */
    @TableField("enable")
    private Boolean enable;

    /**
     * 描述
     */
    @TableField("description")
    private String description;
}
