package com.qsz.bmss.model;

import lombok.Data;

/**
 * @author sherry.xu
 * @Date 2020/6/11 16:22
 */
@Data
public class FormUser {
    private Integer userId;
    private String username;
    private String nickName;
    private String userPhoto;
    private String password;
    private boolean enabled;
    private Integer[] roles;
}
