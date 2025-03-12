package cn.wnhyang.coolGuard.system.entity;

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
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 用户信息表
 *
 * @author wnhyang
 * @since 2023/05/14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_user")
public class UserDO extends BaseDO {

    @Serial
    private static final long serialVersionUID = -8660240418764574220L;

    /**
     * 用户ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户账号
     */
    @TableField("username")
    private String username;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 用户昵称
     */
    @TableField("nickname")
    private String nickname;

    /**
     * 真实姓名
     */
    @TableField("real_name")
    private String realName;

    /**
     * 用户类型
     */
    @TableField("type")
    private Integer type;

    /**
     * 描述
     */
    @TableField("remark")
    private String remark;

    /**
     * 用户邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 手机号码
     */
    @TableField("mobile")
    private String mobile;

    /**
     * 用户性别
     */
    @TableField("sex")
    private Integer sex;

    /**
     * 头像地址
     */
    @TableField("avatar")
    private String avatar;

    /**
     * 帐号状态（0正常 1停用）
     */
    @TableField("status")
    private Boolean status;

    /**
     * 账号过期时间
     */
    @TableField("expire_date")
    private LocalDate expireDate;

    /**
     * 最后登录IP
     */
    @TableField("login_ip")
    private String loginIp;

    /**
     * 最后登录时间
     */
    @TableField("login_date")
    private LocalDateTime loginDate;

}
