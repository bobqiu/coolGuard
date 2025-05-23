package cn.wnhyang.coolguard.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 业务异常错误码，占用 [1 000 000 000, +∞)
 * <p>
 * 一共 10 位，分成四段
 * 第一段，1 位，类型
 * 1 - 业务级别异常
 * x - 预留
 * 第二段，3 位，系统类型
 * 001 - 用户系统
 * 002 - 商品系统
 * 003 - 订单系统
 * 004 - 支付系统
 * 005 - 优惠劵系统
 * ... - ...
 * 第三段，3 位，模块
 * 不限制规则。
 * 一般建议，每个系统里面，可能有多个模块，可以再去做分段。以用户系统为例子：
 * 001 - OAuth2 模块
 * 002 - User 模块
 * 003 - MobileCode 模块
 * 第四段，3 位，错误码
 * 不限制规则。
 * 一般建议，每个模块自增。
 *
 * @author wnhyang
 * @date 2024/3/13
 **/
@Data
@AllArgsConstructor
public class ErrorCode {

    private Integer code;

    private String msg;
}
