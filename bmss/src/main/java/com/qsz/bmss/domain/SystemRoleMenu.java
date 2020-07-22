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
@TableName("sys_role_menu")
public class SystemRoleMenu {
    @TableId
    private Integer id;
    @TableField("menu_id")
    private Integer menuId;
    @TableField("role_id")
    private Integer roleId;

    public SystemRoleMenu(Integer menuId, Integer roleId) {
        this.menuId = menuId;
        this.roleId = roleId;
    }

    public SystemRoleMenu(){

    }

}