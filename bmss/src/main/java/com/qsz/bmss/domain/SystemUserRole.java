/**
 * Program  : SystemUserRole.java
 * Author   : xy.zheng
 * Create   : 2016-6-7 9:38:28
 *
 * Copyright 2016 by SHENZHEN JIUZHOU Electronic CO.,LTD,
 * All rights reserved.
 *
 * The road ahead will be long and our climb will be steep.
 */
package com.qsz.bmss.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("sys_user_role")
public class SystemUserRole {
    @TableId
    private Integer id;
    @TableField("user_id")
    private Integer userId;
    @TableField("role_id")
    private Integer roleId;

    public SystemUserRole(Integer userId, Integer roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }

    public SystemUserRole(){

    }

}