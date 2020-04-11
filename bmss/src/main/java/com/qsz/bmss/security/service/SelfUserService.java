package com.qsz.bmss.security.service;

import com.qsz.bmss.domain.SystemRole;
import com.qsz.bmss.domain.SystemUser;
import com.qsz.bmss.model.SelfUser;
import com.qsz.bmss.service.ISystemUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service("selfUserService")
public class SelfUserService implements UserDetailsService {
    @Autowired
    ISystemUserService systemUserService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SystemUser systemUser = systemUserService.selectUserByUserName(username);
        if (systemUser == null){
            throw new UsernameNotFoundException("用户名不存在！");
        }

        log.info(systemUser.toString());
        SelfUser selfUser = new SelfUser();
        BeanUtils.copyProperties(systemUser,selfUser);
        List<SystemRole> roles = systemUserService.selectSysRoleByUserId(selfUser.getUserId());
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (SystemRole role: roles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getRoleName()));
        }

        selfUser.setAuthorities(authorities);
        log.info(selfUser.toString());
        return selfUser;
    }
}

