package com.qsz.bmss.config;

import org.apache.catalina.User;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserAuthenticationProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userName = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        //查询用户是否存在
        if (!userName.equals("sherry") )
            throw  new UsernameNotFoundException("用户名不存在");
        //判断密码
//        if (!new BCryptPasswordEncoder().matches(password,"$2a$10$5T851lZ7bc2U87zjt/9S6OkwmLW62tLeGLB2aCmq3XRZHA7OI7Dqa"))
        if (!password.equals("123456"))
            throw new BadCredentialsException("密码不正确");
        //判断状态

        //查询用户角色

        return new UsernamePasswordAuthenticationToken(userName,password);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
