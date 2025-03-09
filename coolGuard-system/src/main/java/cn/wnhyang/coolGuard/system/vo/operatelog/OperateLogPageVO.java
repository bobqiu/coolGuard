package cn.wnhyang.coolGuard.system.vo.operatelog;

import cn.hutool.core.date.DatePattern;
import cn.wnhyang.coolGuard.common.pojo.PageParam;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * @author wnhyang
 * @date 2023/8/15
 **/
@Data
public class OperateLogPageVO extends PageParam {

    @Serial
    private static final long serialVersionUID = -1856008201727537612L;

    /**
     * 模块名
     */
    private String module;

    /**
     * 用户昵称
     */
    private String userNickname;

    /**
     * 操作类型
     */
    private Integer type;

    /**
     * 操作结果
     */
    private Integer resultCode;

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
