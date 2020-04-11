package com.qsz.bmss.domain;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.List;


@Data
@TableName("sys_user")
public class SystemUser {

    @TableId
    private Long userId;

    @TableField("user_name")
    private String username;

    @TableField("user_password")
    private String password;

    private String nickName;

    private String userPhoto;

    @TableField("user_status")
    private Boolean enabled;
}
