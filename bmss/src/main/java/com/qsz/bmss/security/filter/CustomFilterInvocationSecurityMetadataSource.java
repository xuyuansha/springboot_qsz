package com.qsz.bmss.security.filter;

import com.qsz.bmss.domain.SystemMenu;
import com.qsz.bmss.domain.SystemRole;
import com.qsz.bmss.service.ISystemMenuService;
import com.qsz.bmss.service.ISystemRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

/**
 * 从url中获取需要的Role
 */
@Slf4j
@Component
public class CustomFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    @Autowired
    ISystemMenuService systemMenuService;
    @Autowired
    ISystemRoleService systemRoleService;

    AntPathMatcher antPathMatcher = new AntPathMatcher();
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        String requstUrl = ((FilterInvocation)object).getRequestUrl();
        log.info(requstUrl);
        if (!"/".equals(requstUrl)){
            List<SystemMenu>  menus = systemMenuService.selectAllSystemMenus ();
            for(SystemMenu menu:menus){
    //            log.info(menu.toString());
                if (antPathMatcher.match(menu.getMenuUrl(),requstUrl)){
                    List<SystemRole>  roles = systemRoleService.selectRolesByMenuId(menu.getMenuId());
                    log.info(roles.toString());
                    if (roles != null) {
                        String[] str = new String[roles.size() + 1];
                        for (int i = 0; i < roles.size(); i++) {
                            str[i] = "ROLE_" + roles.get(i).getRoleName();
                        }
                        str[roles.size()] = "ROLE_ADMIN";
                        return org.springframework.security.access.SecurityConfig.createList(str);
                    }
                }
            }
        }
        return org.springframework.security.access.SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
