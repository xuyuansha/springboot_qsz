package com.qsz.bmss.domain;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;


/*
CREATE TABLE `sys_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT ,
  `role_name` varchar(96) DEFAULT NULL,
  `role_level` tinyint(4) DEFAULT NULL,
  `create_user_id` int(1) DEFAULT NULL,
  `role_desc` varchar(192) DEFAULT NULL,
  `add_time` datetime DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

*/
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

}
