package org.ylc.frame.springbootdemo.base;

import lombok.Data;

import java.util.Date;

/**
 * 系统日志
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2019/4/15 21:17
 */
@Data
public class SystemLog {

    private String userId;

    private String url;

    private String httpMethod;

    private String controller;

    private String method;

    private String ip;

    private String args;

    private long methodTime;

    private long allTime;

    private Date date;

}
