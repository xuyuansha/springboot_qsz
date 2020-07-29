package com.qsz.bmss.controller;

import com.qsz.bmss.model.FormUser;
import com.qsz.bmss.model.QueryParams;
import com.qsz.bmss.model.Result;
import com.qsz.bmss.model.ResultGenerator;
import com.qsz.bmss.service.ISystemLogService;
import com.qsz.bmss.service.ISystemUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author sherry.xu
 * @Date 2020/4/23 14:56
 */
@Slf4j
@RestController
public class LogController {
    @Autowired
    ISystemLogService systemLogService;

    /**
     * 查询用户
     * @param pageNo
     * @param pageSize
     * @param params
     * @return
     */
    @GetMapping(value="/system/log/v1", produces="application/json;charset=UTF-8")
    public Result users(@RequestParam(required = false) Integer pageNo, @RequestParam(required = false) Integer pageSize,  QueryParams params){
        return ResultGenerator.genSuccessResult(systemLogService.getLogs(pageNo,pageSize,params));
    }

}
