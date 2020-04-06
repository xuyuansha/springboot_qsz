package com.qsz.bmss.domain;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;


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
@Getter
@Setter
@Entity
@Table(name="sys_user")
public class SystemUser {
    @Id
    @Column(name="user_id")
    private Integer userId;

    private String userName;

    private String userPassword;

    private String nickName;

    private String userPhoto;

    private Short userStatus;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "sys_user_role",inverseJoinColumns = @JoinColumn(name="role_id"),joinColumns = @JoinColumn(name = "user_id"))
    private List<SystemRole> roleList;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    @Transient
    private Collection<GrantedAuthority> authorities;
}
