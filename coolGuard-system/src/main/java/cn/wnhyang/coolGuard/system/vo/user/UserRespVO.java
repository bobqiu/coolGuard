package cn.wnhyang.coolGuard.system.vo.user;

import cn.wnhyang.coolGuard.common.entity.LabelValue;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

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
     * 角色标识列表
     */
    private Set<String> roles;

    /**
     * 角色列表
     */
    private List<LabelValue> roleList;

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
