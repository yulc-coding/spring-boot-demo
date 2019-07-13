package org.ylc.frame.springbootdemo.mongodb.bean;

import lombok.Data;

import java.util.Date;

/**
 * 系统日志
 * mongodb 记录
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2019/4/15 21:17
 */
@Data
public class SystemLog {

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 请求 url
     */
    private String url;

    /**
     * Http method POST GET
     */
    private String httpMethod;

    /**
     * 请求Controller
     */
    private String controller;

    /**
     * 请求方法
     */
    private String method;

    /**
     * 请求IP
     */
    private String ip;

    /**
     * 请求参数
     */
    private String args;

    /**
     * 方法执行时间
     */
    private long methodTime;

    /**
     * 总耗时
     */
    private long allTime;

    /**
     * 发起时间
     */
    private Date date;

}
