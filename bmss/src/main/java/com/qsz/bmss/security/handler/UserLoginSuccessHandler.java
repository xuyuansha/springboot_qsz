package com.qsz.bmss.security.handler;

import com.qsz.bmss.common.LogDescription;
import com.qsz.bmss.model.ResultGenerator;
import com.qsz.bmss.model.SelfUser;
import com.qsz.bmss.security.config.JWTConfig;
import com.qsz.bmss.security.utils.JWTTokenUtil;
import com.qsz.bmss.security.utils.ResultUtil;
import com.qsz.bmss.service.ISystemLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class UserLoginSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    ISystemLogService systemLogService;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 组装JWT
        SelfUser systemUser = (SelfUser) authentication.getPrincipal();
        String token = JWTTokenUtil.createAccessToken(systemUser);
        token = JWTConfig.tokenPrefix + token;
        systemUser.setToken(token);

        systemLogService.log(LogDescription.S_LOGIN, true,
                LogDescription.D_LOGIN, request.getRemoteAddr());

        ResultUtil.responseJson(response, ResultGenerator.genSuccessResult(systemUser));
    }
}
