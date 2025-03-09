package cn.wnhyang.coolGuard.system.vo.loginlog;

import cn.hutool.core.date.DatePattern;
import cn.wnhyang.coolGuard.common.pojo.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * @author wnhyang
 * @date 2023/8/15
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class LoginLogPageVO extends PageParam {

    @Serial
    private static final long serialVersionUID = -3755641368582812482L;

    /**
     * 登录类型
     */
    private Integer loginType;

    /**
     * 登录ip
     */
    private String userIp;

    /**
     * 账号
     */
    private String account;

    /**
     * 结果
     */
    private Integer result;

    /**
     * 开始时间
     */
    @DateTimeFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private LocalDateTime endTime;
}
