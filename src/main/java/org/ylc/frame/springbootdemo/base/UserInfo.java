package org.ylc.frame.springbootdemo.base;

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

    private String userId;

    private String account;

    private String userName;

    private String depId;

    public UserInfo() {
    }

    public UserInfo(String userId, String account, String userName, String depId) {
        this.userId = userId;
        this.account = account;
        this.userName = userName;
        this.depId = depId;
    }
}
