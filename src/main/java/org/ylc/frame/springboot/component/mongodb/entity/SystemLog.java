package org.ylc.frame.springboot.component.mongodb.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 * <p>
 * 系统日志
 *
 * @author yulc
 * @version 1.0.0
 * @date 2019/9/24
 */
@Getter
@Setter
@Document(collection = "systemLog")
public class SystemLog implements Serializable {

    private static final long serialVersionUID = -5944358835830751073L;

    /**
     * 唯一ID
     */
    private String id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 登录方式：1PC，2APP
     */
    private String loginFrom;

    /**
     * 访问路径
     */
    private String url;

    /**
     * 请求方式 post、get
     */
    private String httpMethod;

    /**
     * Controller路径
     */
    private String controller;
    /**
     * 调用方法
     */
    private String method;
    /**
     * 调用者IP
     */
    private String ip;
    /**
     * 请求参数
     */
    private String args;

    /**
     * 方法耗时
     */
    private long methodTime;

    /**
     * 总耗时
     */
    private long allTime;

    /**
     * 操作时间
     */
    private Date date;

}
