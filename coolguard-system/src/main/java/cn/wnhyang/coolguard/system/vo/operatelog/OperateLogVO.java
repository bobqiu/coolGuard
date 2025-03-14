package cn.wnhyang.coolguard.system.vo.operatelog;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author wnhyang
 * @date 2023/8/15
 **/
@Data
public class OperateLogVO {

    /**
     * id
     */
    private Long id;

    /**
     * 链路id
     */
    private String traceId;

    /**
     * 用户昵称
     */
    private String userNickname;

    /**
     * 用户编号
     */
    private Long userId;

    /**
     * 操作模块
     */
    @NotBlank(message = "操作模块不能为空")
    private String module;

    /**
     * 操作名
     */
    @NotBlank(message = "操作名")
    private String name;

    /**
     * 操作类型
     */
    @NotNull(message = "操作分类不能为空")
    private Integer type;

    /**
     * 请求方法名
     */
    @NotBlank(message = "请求方法名不能为空")
    private String requestMethod;

    /**
     * 请求地址
     */
    @NotBlank(message = "请求地址不能为空")
    private String requestUrl;

    private String requestParams;

    /**
     * 用户 IP
     */
    @NotBlank(message = "用户 IP 不能为空")
    private String userIp;

    /**
     * 浏览器
     */
    @NotBlank(message = "浏览器 UserAgent 不能为空")
    private String userAgent;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 执行时长
     */
    @NotNull(message = "执行时长不能为空")
    private Integer duration;

    /**
     * 结果码
     */
    @NotNull(message = "结果码不能为空")
    private Integer resultCode;

    /**
     * 结果信息
     */
    private String resultMsg;

    /**
     * 结果数据
     */
    private String resultData;

}
