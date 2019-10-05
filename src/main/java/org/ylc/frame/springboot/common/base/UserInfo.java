package org.ylc.frame.springboot.common.base;

import lombok.Data;

import java.io.Serializable;

/**
 * 本都用户信息
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2019/4/14 10:52
 */
@Data
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户账号
     */
    private String account;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 部门ID
     */
    private String depId;

    public UserInfo() {
    }

    public UserInfo(Long userId, String account, String userName, String depId) {
        this.userId = userId;
        this.account = account;
        this.userName = userName;
        this.depId = depId;
    }
}
