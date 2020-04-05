package com.qsz.bmss.domain;

import lombok.Data;



/*
CREATE TABLE `sys_user` (
        `user_id`  int(11) NOT NULL AUTO_INCREMENT,
        `user_name` varchar(48) DEFAULT NULL,
        `user_password` varchar(96) DEFAULT NULL,
        `nick_name` varchar(72) DEFAULT NULL,
        `user_photo` varchar(384) DEFAULT NULL,
        `user_status` tinyint(4) DEFAULT NULL,
        `parent_user_name` varchar(48) DEFAULT NULL,
        `child_max_count` tinyint(4) DEFAULT NULL,
        `add_time` datetime DEFAULT NULL,
        PRIMARY KEY (`user_id`)
        ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
*/
@Data
public class SystemUser {
}
