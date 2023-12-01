package cn.ilovecry.cloudpractice.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;

/**
 * GlobalExceptionHandler
 *
 * @author yangyi
 * @version 1.0
 * @date 2023/12/1 17:03
 */
@RestControllerAdvice
public class GlobalExceptionResolver {
    private final static Logger log = LoggerFactory.getLogger(GlobalExceptionResolver.class);

    @ExceptionHandler(Exception.class)
    public R defaultHandler(Exception e) {
        log.error("未知异常", e);
        return R.error("服务繁忙，请稍后再试");
    }

    @ExceptionHandler(AuthenticationException.class)
    public R authenticationExceptionHandler(AuthenticationException e) {
        log.error("认证异常:{}", e.getMessage());
        return R.error(R.UNAUTHORIZED_CODE, e.getMessage());
    }
}
