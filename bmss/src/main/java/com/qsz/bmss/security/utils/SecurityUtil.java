package com.qsz.bmss.security.utils;

import com.qsz.bmss.model.SelfUser;
import org.springframework.security.core.context.SecurityContextHolder;


public class SecurityUtil {
    private SecurityUtil(){

    }

    /**
     * 获取当前用户信息
     * @return
     */
    public static SelfUser getUserInfo(){
        SelfUser systemUser = (SelfUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

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
