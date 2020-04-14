package com.qsz.bmss.security.handler;

import com.qsz.bmss.model.ResultCode;
import com.qsz.bmss.model.ResultGenerator;
import com.qsz.bmss.security.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
@Slf4j
public class UserAuthenticationEntryPointHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ResultUtil.responseJson(response, ResultGenerator.genFailResult(ResultCode.UNAUTHORIZED,"未登录"));
    }
}
