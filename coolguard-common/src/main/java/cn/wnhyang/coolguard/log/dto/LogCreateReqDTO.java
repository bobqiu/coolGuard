package cn.wnhyang.coolguard.log.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author wnhyang
 * @date 2024/1/5
 **/
@Data
public class LogCreateReqDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 541323985520059827L;

    @NotBlank(message = "链路id不能为空")
    private String traceId;

    @NotNull(message = "用户编号不能为空")
    private Long userId;

    private String userNickname;

    @NotBlank(message = "操作模块不能为空")
    private String module;

    @NotBlank(message = "操作名")
    private String name;

    @NotNull(message = "操作分类不能为空")
    private Integer type;

    @NotBlank(message = "请求方法名不能为空")
    private String requestMethod;

    @NotBlank(message = "请求地址不能为空")
    private String requestUrl;

    private String requestParams;

    @NotBlank(message = "用户 IP 不能为空")
    private String userIp;

    @NotBlank(message = "浏览器 UserAgent 不能为空")
    private String userAgent;

    @NotNull(message = "开始时间不能为空")
    private LocalDateTime startTime;

    @NotNull(message = "结束时间不能为空")
    private LocalDateTime endTime;

    @NotNull(message = "执行时长不能为空")
    private Integer duration;

    @NotNull(message = "结果码不能为空")
    private Integer resultCode;

    private String resultMsg;

    /**
     * 结果数据
     */
    private String resultData;
}
