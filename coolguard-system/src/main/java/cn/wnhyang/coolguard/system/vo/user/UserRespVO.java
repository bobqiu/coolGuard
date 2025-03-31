package cn.wnhyang.coolguard.system.vo.user;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author wnhyang
 * @date 2023/8/9
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ExcelIgnoreUnannotated
public class UserRespVO extends UserCreateVO {

    /**
     * 用户id
     */
    private Long id;

    /**
     * 登录ip
     */
    private String loginIp;

    /**
     * 登录时间
     */
    private LocalDateTime loginDate;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
