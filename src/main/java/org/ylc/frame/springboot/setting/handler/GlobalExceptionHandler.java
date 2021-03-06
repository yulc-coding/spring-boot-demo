package org.ylc.frame.springboot.setting.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.ylc.frame.springboot.biz.common.entity.HttpResult;
import org.ylc.frame.springboot.setting.exception.CheckException;
import org.ylc.frame.springboot.setting.exception.OperationException;

import javax.validation.ConstraintViolationException;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 * <p>
 * 全局controller层异常处理
 * 执行与抛出异常血缘关系最近的方法
 *
 * @author yulc
 * @version 1.0.0
 * @date 2019/9/24
 */
@Component
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(OperationException.class)
    public HttpResult<String> handler(OperationException e) {
        String error = e.getMessage();
        log.error("自定义错误信息:{}", error);
        return HttpResult.fail(e.getErrCode(), error);
    }

    @ExceptionHandler(CheckException.class)
    public HttpResult<String> handler(CheckException e) {
        String error = e.getMessage();
        log.error("自定义校验错误:{}", error);
        return HttpResult.fail(e.getErrCode(), error);
    }

    /**
     * 参数校验 @Validated 异常处理
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public HttpResult<String> handle(MethodArgumentNotValidException e) {
        String error = e.getBindingResult().getFieldError().getDefaultMessage();
        log.error("方法参数校验异常:{}", error);
        return HttpResult.fail(error);
    }

    /**
     * 处理参数不合法参数异常
     *
     * @param ex 参数不合法异常
     * @return HttpResult.fail
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public HttpResult<String> illegalArgumentExceptionHandler(IllegalArgumentException ex) {
        return HttpResult.fail(ex.getMessage());
    }

    /**
     * 参数类型不匹配
     *
     * @param ex 异常
     * @return HttpResult.fail
     */
    @ExceptionHandler(TypeMismatchException.class)
    public HttpResult<String> requestTypeMismatch(TypeMismatchException ex) {
        return HttpResult.fail("参数类型不匹配,参数" + ex.getPropertyName() + "类型应该为" + ex.getRequiredType()
                + "，实际传入参数：" + ex.getValue());
    }

    /**
     * @param ex 缺少的参数名称
     * @return HttpResult.fail
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public HttpResult<String> requestParameterExceptionHandler(MissingServletRequestParameterException ex) {
        return HttpResult.fail("缺少必填参数：" + ex.getParameterName() + "，参数类型为：" + ex.getParameterType());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public HttpResult<String> handlerHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        String error = e.getMessage();
        log.error("不支持当前请求方法:{}", error);
        return HttpResult.fail("不支持当前请求方法");
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public HttpResult<String> handlerHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        String error = e.getMessage();
        log.error("不支持当前媒体类型:{}", error);
        return HttpResult.fail("不支持当前媒体类型");
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public HttpResult<String> handlerConstraintViolationException(ConstraintViolationException e) {
        String error = e.getMessage();
        log.error("约束冲突异常:{}", error);
        return HttpResult.fail("约束冲突异常");
    }

    /**
     * 处理其他所有异常
     *
     * @param ex 所有异常
     * @return HttpResult.fail
     */
    @ExceptionHandler(Exception.class)
    public HttpResult<String> exceptionHandler(Exception ex) {
        String error = ex.getMessage();
        log.error("系统异常:{}", error);
        ex.printStackTrace();
        return HttpResult.fail(error);
    }
}
