package com.qsz.bmss.domain;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
//@Getter
//@Setter
@Entity
@Table(name="sys_user")
public class SystemUser implements UserDetails {
    @Id
    @Column(name="user_id")
    private Integer userId;

    @Column(name="user_name")
    private String username;

    @Column(name="user_password")
    private String password;

    private String nickName;

    private String userPhoto;

    @Column(name = "user_status")
    private Boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "sys_user_role",inverseJoinColumns = @JoinColumn(name="role_id"),joinColumns = @JoinColumn(name = "user_id"))
    private List<SystemRole> roleList;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    @Transient
    private Collection<GrantedAuthority> authorities;

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public List<SystemRole> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<SystemRole> roleList) {
        this.roleList = roleList;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
