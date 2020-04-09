package com.qsz.bmss.service.impl;

import com.qsz.bmss.dao.ISystemMenuDao;
import com.qsz.bmss.dao.ISystemUserDao;
import com.qsz.bmss.domain.SystemMenu;
import com.qsz.bmss.domain.SystemRole;
import com.qsz.bmss.domain.SystemUser;
import com.qsz.bmss.utils.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.asn1.x509.UserNotice;
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

@Service
@Slf4j
public class SystemUserDetailsService  implements UserDetailsService {
    @Autowired
    private ISystemUserDao systemUserDao;
    @Autowired
    private ISystemMenuDao systemMenuDao;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SystemUser systemUser = systemUserDao.findUserByUsername(username);
        log.info(systemUser.toString());
        if (systemUser == null){
            throw new UsernameNotFoundException("用户名不存在！");
        }
        List<SystemRole> roles = systemUser.getRoleList();
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (SystemRole role: roles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getRoleName()));
        }
        systemUser.setAuthorities(authorities);
        return systemUser;
    }

    public List<SystemMenu> listUserMenus() {
        List<SystemMenu> menuByRoleList = systemMenuDao.findMenuByUserId(SecurityUtil.getUserId());
        log.info("--->"+menuByRoleList);
        return menuByRoleList;

    }
}
