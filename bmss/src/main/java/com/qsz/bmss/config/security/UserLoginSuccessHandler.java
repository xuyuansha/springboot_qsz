package com.qsz.bmss.config.security;

import com.qsz.bmss.domain.SystemUser;
import com.qsz.bmss.utils.JWTTokenUtil;
import com.qsz.bmss.utils.ResultUtil;
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
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 组装JWT
        SystemUser systemUser = (SystemUser) authentication.getPrincipal();
        String token = JWTTokenUtil.createAccessToken(systemUser);
        token = JWTConfig.tokenPrefix + token;
        Map<String,Object> resultData = new HashMap<>();
        resultData.put("code",200);
        resultData.put("msg", "登录成功");
        resultData.put("user",token);

        ResultUtil.responseJson(response,resultData);
    }
}
