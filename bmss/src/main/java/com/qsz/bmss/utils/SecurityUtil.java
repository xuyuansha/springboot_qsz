package com.qsz.bmss.utils;

import com.qsz.bmss.domain.SystemUser;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.criteria.CriteriaBuilder;

public class SecurityUtil {
    private SecurityUtil(){

    }

    /**
     * 获取当前用户信息
     * @return
     */
    public static SystemUser getUserInfo(){
        SystemUser systemUser = (SystemUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return systemUser;
    }

    /**
     * 获取当前用户ID
     */
    public static Integer getUserId(){
        return getUserInfo().getUserId();
    }
    /**
     * 获取当前用户账号
     */
    public static String getUserName(){
        return getUserInfo().getUsername();
    }
}
