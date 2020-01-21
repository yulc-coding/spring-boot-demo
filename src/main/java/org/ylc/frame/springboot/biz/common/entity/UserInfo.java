package org.ylc.frame.springboot.biz.common.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 本地用户信息
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2019/4/14 10:52
 */
@Getter
@Setter
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 7655679675008142448L;
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户账号
     */
    private String username;

    /**
     * 用户名称
     */
    private String name;

    /**
     * 部门ID
     */
    private String depCode;

    public UserInfo(Long userId, String username, String name, String depCode) {
        this.userId = userId;
        this.username = username;
        this.name = name;
        this.depCode = depCode;
    }
}
