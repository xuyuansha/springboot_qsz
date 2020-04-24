package com.qsz.bmss.controller;

import com.qsz.bmss.model.Result;
import com.qsz.bmss.model.ResultGenerator;
import com.qsz.bmss.security.utils.ResultUtil;
import com.qsz.bmss.service.ISystemUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sherry.xu
 * @Date 2020/4/23 14:56
 */
@Slf4j
@RestController
public class UserController {
    @Autowired
    ISystemUserService userService;

    @GetMapping("/system/user")
    public Result users(){
        return ResultGenerator.genSuccessResult(userService.selectAllUser());
    }
}
