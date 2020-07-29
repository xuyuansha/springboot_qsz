package com.qsz.bmss.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("sys_log")
public class SystemLog {
    @TableId
    private Integer logId;


    private Integer sysUserid;

    @TableField(exist = false)
    private String userName;

    private String operateSummary;

    private String operateDesc;

    private String operateState;

    private String operateDate;

}