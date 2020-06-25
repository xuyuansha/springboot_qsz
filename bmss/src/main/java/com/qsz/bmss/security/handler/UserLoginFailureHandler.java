package com.qsz.bmss.security.handler;

import com.qsz.bmss.model.ResultCode;
import com.qsz.bmss.model.ResultGenerator;
import com.qsz.bmss.security.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class UserLoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        // 这些对于操作的处理类可以根据不同异常进行不同处理
//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//        String pwd = bCryptPasswordEncoder.encode("e10adc3949ba59abbe56e057f20f883e");
//        log.info(pwd);
        if (exception instanceof UsernameNotFoundException){
            log.info("【登录失败】"+exception.getMessage());
            ResultUtil.responseJson(response, ResultGenerator.genFailResult(ResultCode.LOGIN_ERROR,"用户不存在"));
        }
        if (exception instanceof LockedException){
            log.info("【登录失败】"+exception.getMessage());
            ResultUtil.responseJson(response, ResultGenerator.genFailResult(ResultCode.LOGIN_ERROR,"用户被冻结"));
        }
        if (exception instanceof BadCredentialsException){
            log.info("【登录失败】"+exception.getMessage());
            ResultUtil.responseJson(response, ResultGenerator.genFailResult(ResultCode.LOGIN_ERROR,"用户名或密码不正确"));
        }
        if (exception instanceof DisabledException) {
            log.info("【登录失败】"+exception.getMessage());
            ResultUtil.responseJson(response, ResultGenerator.genFailResult(ResultCode.LOGIN_ERROR,"用户被禁用"));
        }

        log.info(exception.toString());
        ResultUtil.responseJson(response, ResultGenerator.genFailResult(ResultCode.LOGIN_ERROR,"登录失败"));
    }
}
