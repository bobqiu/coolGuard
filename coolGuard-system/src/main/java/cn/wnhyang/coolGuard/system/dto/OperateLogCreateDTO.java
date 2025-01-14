package cn.wnhyang.coolGuard.system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author wnhyang
 * @date 2023/6/6
 **/
@Data
public class OperateLogCreateDTO implements Serializable {

    private static final long serialVersionUID = 541323985520059827L;

    @NotNull(message = "用户编号不能为空")
    private Long userId;

    private String userNickname;

    @NotBlank(message = "操作模块不能为空")
    private String module;

    @NotBlank(message = "操作名")
    private String name;

    @NotNull(message = "操作分类不能为空")
    private Integer type;

    private String content;

    private Map<String, Object> exts;

    @NotBlank(message = "请求方法名不能为空")
    private String requestMethod;

    @NotBlank(message = "请求地址不能为空")
    private String requestUrl;

    @NotBlank(message = "用户 IP 不能为空")
    private String userIp;

    @NotBlank(message = "浏览器 UserAgent 不能为空")
    private String userAgent;

    @NotBlank(message = "Java 方法名不能为空")
    private String javaMethod;

    private String javaMethodArgs;

    @NotNull(message = "开始时间不能为空")
    private LocalDateTime startTime;

    @NotNull(message = "结束时间不能为空")
    private LocalDateTime endTime;

    @NotNull(message = "执行时长不能为空")
    private Integer duration;

    @NotNull(message = "结果码不能为空")
    private Integer resultCode;

    private String resultMsg;

    private String resultData;

}
