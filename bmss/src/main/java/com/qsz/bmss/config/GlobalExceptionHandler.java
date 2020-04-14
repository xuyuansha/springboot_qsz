package com.qsz.bmss.config;

import com.qsz.bmss.model.Result;
import com.qsz.bmss.model.ResultGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * @author sherry.xu
 * @Date 2020/4/14 10:07
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ResponseBody
    @ExceptionHandler(ServiceException.class)
    public Result globalException(HttpServletResponse response, ServiceException ex){
        return ResultGenerator.genFailResult(ex.getMessage());
    }

}
