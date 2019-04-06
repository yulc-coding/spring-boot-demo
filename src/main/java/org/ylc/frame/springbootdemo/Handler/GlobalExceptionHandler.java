package org.ylc.frame.springbootdemo.Handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.ylc.frame.springbootdemo.base.HttpResult;

import javax.validation.ConstraintViolationException;

/**
 * 全局controller层异常处理
 * 执行与抛出异常血缘关系最近的方法
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2019/3/30 11:23
 */
@Component
@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 处理参数不合法参数异常
     *
     * @param ex 参数不合法异常
     * @return HttpResult.fail
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public HttpResult illegalArgumentExceptionHandler(IllegalArgumentException ex) {
        return HttpResult.fail(ex.getLocalizedMessage());
    }

    /**
     * 参数类型不匹配
     *
     * @param ex 异常
     * @return HttpResult.fail
     */
    @ExceptionHandler(TypeMismatchException.class)
    @ResponseBody
    public HttpResult requestTypeMismatch(TypeMismatchException ex) {
        return HttpResult.fail("参数类型不匹配,参数" + ex.getPropertyName() + "类型应该为" + ex.getRequiredType()
                + "，实际传入参数：" + ex.getValue());
    }

    /**
     * @param ex 缺少的参数名称
     * @return HttpResult.fail
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public HttpResult requestParameterExceptionHandler(MissingServletRequestParameterException ex) {
        return HttpResult.fail("缺少必填参数：" + ex.getParameterName() + "，参数类型为：" + ex.getParameterType());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public HttpResult handlerHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        String error = e.getLocalizedMessage();
        log.error("不支持当前请求方法:{}", error);
        return HttpResult.fail("不支持当前请求方法");
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseBody
    public HttpResult handlerHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        String error = e.getLocalizedMessage();
        log.error("不支持当前媒体类型:{}", error);
        return HttpResult.fail("不支持当前媒体类型");
    }

    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
    @ResponseBody
    public HttpResult handlerInvalidDataAccessApiUsageException(InvalidDataAccessApiUsageException e) {
        String error = e.getLocalizedMessage();
        log.error("数据库操作失败:{}", error);
        return HttpResult.fail("数据库操作失败");
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public HttpResult handlerConstraintViolationException(ConstraintViolationException e) {
        String error = e.getLocalizedMessage();
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
    @ResponseBody
    public ResponseEntity exceptionHandler(Exception ex) {
        log.debug(ex.getLocalizedMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
