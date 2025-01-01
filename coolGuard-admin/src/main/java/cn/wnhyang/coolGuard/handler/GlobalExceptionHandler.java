package cn.wnhyang.coolGuard.handler;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import cn.dev33.satoken.exception.SameTokenInvalidException;
import cn.wnhyang.coolGuard.exception.ServiceException;
import cn.wnhyang.coolGuard.pojo.CommonResult;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static cn.wnhyang.coolGuard.exception.GlobalErrorCode.*;


/**
 * @author wnhyang
 * @date 2023/8/8
 **/
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 权限码异常
     */
    @ExceptionHandler(NotPermissionException.class)
    public ResponseEntity<CommonResult<Void>> handleNotPermissionException(NotPermissionException e, HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        log.error("请求地址'{}',权限码校验失败'{}'", requestUri, e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(CommonResult.error(FORBIDDEN));
    }

    /**
     * 角色权限异常
     */
    @ExceptionHandler(NotRoleException.class)
    public ResponseEntity<CommonResult<Void>> handleNotRoleException(NotRoleException e, HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        log.error("请求地址'{}',角色权限校验失败'{}'", requestUri, e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(CommonResult.error(FORBIDDEN));
    }

    /**
     * 认证失败
     */
    @ExceptionHandler(NotLoginException.class)
    public ResponseEntity<CommonResult<Void>> handleNotLoginException(NotLoginException e, HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        log.error("请求地址'{}',认证失败'{}',无法访问系统资源", requestUri, e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(CommonResult.error(UNAUTHORIZED));
    }

    /**
     * 无效认证
     */
    @ExceptionHandler(SameTokenInvalidException.class)
    public ResponseEntity<CommonResult<Void>> handleSameTokenInvalidException(SameTokenInvalidException e, HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        log.error("请求地址'{}',内网认证失败'{}',无法访问系统资源", requestUri, e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(CommonResult.error(UNAUTHORIZED));
    }

    /**
     * 请求方式不支持
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<CommonResult<Void>> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        log.error("请求地址'{}',不支持'{}'请求", requestUri, e.getMethod());
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(CommonResult.error(METHOD_NOT_ALLOWED));
    }

    /**
     * 处理 SpringMVC 请求参数缺失
     * 例如说，接口上设置了 @RequestParam("xx") 参数，结果并未传递 xx 参数
     */
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public ResponseEntity<CommonResult<Void>> missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException e, HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        log.error("请求路径中缺少必需的路径变量'{}',发生系统异常.", requestUri, e);
        return ResponseEntity.badRequest().body(CommonResult.error(BAD_REQUEST.getCode(), String.format("请求参数缺失:%s", e.getParameterName())));
    }

    /**
     * 请求路径中缺少必需的路径变量
     */
    @ExceptionHandler(MissingPathVariableException.class)
    public ResponseEntity<CommonResult<Void>> handleMissingPathVariableException(MissingPathVariableException e, HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        log.error("请求路径中缺少必需的路径变量'{}',发生系统异常.", requestUri, e);
        return ResponseEntity.badRequest().body(CommonResult.error(BAD_REQUEST.getCode(), String.format("请求路径中缺少必需的路径变量:%s", e.getVariableName())));
    }

    /**
     * 请求参数类型不匹配
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<CommonResult<Void>> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e, HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        log.error("请求参数类型不匹配'{}',发生系统异常.", requestUri, e);
        return ResponseEntity.badRequest().body(CommonResult.error(BAD_REQUEST.getCode(), String.format("请求参数类型错误:%s", e.getMessage())));
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonResult<Void>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        FieldError fieldError = e.getBindingResult().getFieldError();
        // 断言，避免告警
        assert fieldError != null;
        return ResponseEntity.badRequest().body(CommonResult.error(BAD_REQUEST.getCode(), String.format("请求参数不正确:%s", fieldError.getDefaultMessage())));
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(BindException.class)
    public ResponseEntity<CommonResult<Void>> handleBindException(BindException e) {
        log.error(e.getMessage(), e);
        FieldError fieldError = e.getFieldError();
        // 断言，避免告警
        assert fieldError != null;
        return ResponseEntity.badRequest().body(CommonResult.error(BAD_REQUEST.getCode(), String.format("请求参数不正确:%s", fieldError.getDefaultMessage())));
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<CommonResult<Void>> handleServiceException(ServiceException e, HttpServletRequest request) {
        log.error(e.getMessage(), e);
        return ResponseEntity.ok(CommonResult.error(e.getCode(), e.getMessage()));
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<CommonResult<Void>> handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        log.error("请求地址'{}',发生未知异常.", requestUri, e);
        return ResponseEntity.internalServerError().body(CommonResult.error(INTERNAL_SERVER_ERROR.getCode(), e.getMessage()));
    }

    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResult<Void>> handleException(Exception e, HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        log.error("请求地址'{}',发生系统异常.", requestUri, e);
        return ResponseEntity.internalServerError().body(CommonResult.error(INTERNAL_SERVER_ERROR));
    }
}
