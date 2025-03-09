package cn.wnhyang.coolGuard.decision.entity;

import cn.wnhyang.coolGuard.mybatis.BaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;

/**
 * 名单数据表
 *
 * @author wnhyang
 * @since 2024/05/28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("de_list_data")
public class ListData extends BaseDO {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 名单集id
     */
    @TableField("list_set_code")
    private String listSetCode;

    /**
     * 名单数据
     */
    @TableField("value")
    private String value;

    /**
     * 名单数据来源
     */
    @TableField("source")
    private String source;

    /**
     * 描述
     */
    @TableField("description")
    private String description;
}
