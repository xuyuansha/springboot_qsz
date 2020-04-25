package com.qsz.bmss.controller;

import com.qsz.bmss.model.QueryParams;
import com.qsz.bmss.model.Result;
import com.qsz.bmss.model.ResultGenerator;
import com.qsz.bmss.security.utils.ResultUtil;
import com.qsz.bmss.service.ISystemUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author sherry.xu
 * @Date 2020/4/23 14:56
 */
@Slf4j
@RestController
public class UserController {
    @Autowired
    ISystemUserService userService;

    /**
     * 查询用户
     * @param pageNo
     * @param pageSize
     * @param params
     * @return
     */
    @GetMapping(value="/system/user/v1", produces="application/json;charset=UTF-8")
    public Result users(@RequestParam(required = false) Integer pageNo, @RequestParam(required = false) Integer pageSize,  QueryParams params){
        return ResultGenerator.genSuccessResult(userService.selectAllUser(pageNo,pageSize,params));
    }

    /**
     * 添加用户
     * @return
     */
    @PostMapping(value = "/system/user/v1", produces = "application/json;charset=UTF-8")
    public Result addUser(){
        return ResultGenerator.genSuccessResult();
    }
}
