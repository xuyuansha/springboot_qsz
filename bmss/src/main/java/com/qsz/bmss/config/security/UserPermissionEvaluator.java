package com.qsz.bmss.config.security;

import com.qsz.bmss.domain.SystemMenu;
import com.qsz.bmss.domain.SystemUser;
import com.qsz.bmss.service.ISystemMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

@Slf4j
@Component
public class UserPermissionEvaluator implements PermissionEvaluator {
    @Autowired
    private ISystemMenuService systemMenuService;
    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        SystemUser systemUser = (SystemUser) authentication.getPrincipal();
        List<SystemMenu> menus = systemMenuService.selectSysMenuByUserId(systemUser.getUserId());
        log.info(menus.toString()+"--->"+systemUser);
        if(menus.contains(targetDomainObject)) {
            return true;
        }

        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }
}
