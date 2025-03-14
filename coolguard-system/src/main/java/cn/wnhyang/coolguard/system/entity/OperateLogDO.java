package cn.wnhyang.coolguard.system.entity;

import cn.wnhyang.coolguard.mybatis.BaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 操作日志记录
 *
 * @author wnhyang
 * @since 2023/06/05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_operate_log")
public class OperateLogDO extends BaseDO {

    /**
     * {@link #requestParams} 的最大长度
     */
    public static final Integer REQUEST_PARAMS_MAX_LENGTH = 8000;

    /**
     * {@link #resultMsg} 的最大长度
     */
    public static final Integer RESULT_MSG_MAX_LENGTH = 512;

    /**
     * {@link #resultData} 的最大长度
     */
    public static final Integer RESULT_DATA_MAX_LENGTH = 512;

    @Serial
    private static final long serialVersionUID = 1797386582905133955L;

    /**
     * 日志主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 链路id
     */
    @TableField("trace_id")
    private String traceId;

    /**
     * 用户编号
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 用户昵称
     */
    @TableField("user_nickname")
    private String userNickname;

    /**
     * 模块标题
     */
    @TableField("module")
    private String module;

    /**
     * 操作名
     */
    @TableField("name")
    private String name;

    /**
     * 操作分类
     */
    @TableField("type")
    private Integer type;

    /**
     * 请求方法名
     */
    @TableField("request_method")
    private String requestMethod;

    /**
     * 请求地址
     */
    @TableField("request_url")
    private String requestUrl;

    /**
     * 请求参数
     */
    @TableField("request_params")
    private String requestParams;

    /**
     * 用户 IP
     */
    @TableField("user_ip")
    private String userIp;

    /**
     * 浏览器 UA
     */
    @TableField("user_agent")
    private String userAgent;

    /**
     * 操作时间
     */
    @TableField("start_time")
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @TableField("end_time")
    private LocalDateTime endTime;

    /**
     * 执行时长
     */
    @TableField("duration")
    private Integer duration;

    /**
     * 结果码
     */
    @TableField("result_code")
    private Integer resultCode;

    /**
     * 结果提示
     */
    @TableField("result_msg")
    private String resultMsg;

    /**
     * 结果数据
     */
    @TableField("result_data")
    private String resultData;

}
