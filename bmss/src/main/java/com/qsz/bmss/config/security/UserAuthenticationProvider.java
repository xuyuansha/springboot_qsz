package com.qsz.bmss.config.security;

import com.qsz.bmss.domain.SystemRole;
import com.qsz.bmss.domain.SystemUser;
import com.qsz.bmss.service.IUserService;
//import com.qsz.bmss.service.impl.UserService;
import com.qsz.bmss.service.impl.SystemUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Component
public class UserAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private SystemUserDetailsService systemUserDetailsService;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userName = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();
        log.info(userName+password);
        //查询用户是否存在
        SystemUser user = (SystemUser) systemUserDetailsService.loadUserByUsername(userName);
        log.info(user+"");

        if (user == null)
            throw  new UsernameNotFoundException("用户名不存在");
        //判断密码
        if (!new BCryptPasswordEncoder().matches(password,user.getPassword()))
            throw new BadCredentialsException("密码不正确");
        //判断状态g
        if (!user.isEnabled())
            throw new LockedException("该用户已被冻结");

        //查询用户角色
        List<SystemRole> roles = user.getRoleList();
        Set<GrantedAuthority> authorities = new HashSet<>();

//        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        for (SystemRole role: roles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getRoleName()));
        }
        user.setAuthorities(authorities);
        log.info(authorities.toString());
        return new UsernamePasswordAuthenticationToken(user,user.getUserId(),authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
