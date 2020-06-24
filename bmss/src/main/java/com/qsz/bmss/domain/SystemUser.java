package com.qsz.bmss.domain;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.beans.Transient;
import java.util.Date;
import java.util.List;


@Data
@TableName("sys_user")
public class SystemUser {

    @TableId
    private Integer userId;

    @TableField("user_name")
    private String username;

    @TableField("user_password")
    private String password;

    private String nickName;

    private String userPhoto;

    @TableField("user_status")
    private Boolean enabled;

    @TableField(exist = false)
    private List<SystemRole> roles;

    private String addTime;
}
