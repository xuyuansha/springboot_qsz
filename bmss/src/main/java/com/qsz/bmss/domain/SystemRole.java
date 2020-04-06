package com.qsz.bmss.domain;

import com.alibaba.fastjson.JSON;
import lombok.*;
import org.apache.catalina.User;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;


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
@Getter
@Setter
@Entity
@Table(name="sys_role")
public class SystemRole {
    @Id
    @Column(name="role_id")
    private Integer roleId;

    private Integer createUserId;

    private String roleName;

    private String roleDesc;

    private Short roleLevel;

    /*@ManyToMany(mappedBy = "roleList", fetch = FetchType.EAGER)
    private List<SystemUser> userList;*/

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    /*@ManyToMany(cascade = CascadeType.REFRESH)
    @JoinTable(name = "sys_role_menu",inverseJoinColumns = @JoinColumn(name = "menu_id"),joinColumns = @JoinColumn(name = "role_id"))
    private List<SystemMenu> menuList;*/
}
