/**
 * Program  : User.java
 * Author   : xy.zheng
 * Create   : 2016-6-7 15:02:25
 *
 * Copyright 2016 by SHENZHEN JIUZHOU Electronic CO.,LTD,
 * All rights reserved.
 *
 * The road ahead will be long and our climb will be steep.
 */
package com.qsz.bmss.model;
import com.qsz.bmss.domain.SystemUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class User extends SystemUser {

    private Boolean checkbox;
    private Boolean modify;
    private Boolean delete;
    private String addTime2;
    private Byte level;

}
