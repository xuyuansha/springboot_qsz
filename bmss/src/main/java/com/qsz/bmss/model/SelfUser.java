package com.qsz.bmss.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
public class SelfUser implements UserDetails {

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private transient String password;

    private String nickName;

    private String userPhoto;

    private String token;


    /**
     * 用户角色
     */
   
    private transient  Collection<GrantedAuthority> authorities;
    /**
     * 账户是否过期
     */
   
    private transient  boolean isAccountNonExpired = true;
    /**
     * 账户是否被锁定
     */
   
    private transient  boolean isAccountNonLocked = true;
    /**
     * 证书是否过期
     */
   
    private transient  boolean isCredentialsNonExpired = true;
    /**
     * 账户是否有效
     */
   
    private transient  boolean enabled = true;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

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
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
