package com.qsz.bmss.domain;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

@Data
@TableName("sys_role")
public class SystemRole {
    @TableId
    private Integer roleId;

    private Integer createUserId;

    private String roleName;

    private String roleDesc;

    private String roleLabel;

    private Short roleLevel;

    private String addTime;


}
