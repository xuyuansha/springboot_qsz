package com.qsz.bmss.controller;

import com.qsz.bmss.domain.SystemMenu;
import com.qsz.bmss.service.IUserService;
import com.qsz.bmss.utils.ResultUtil;
import com.qsz.bmss.utils.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class UserController {
    @Autowired
    IUserService userService;
    @RequestMapping("/hello")
    public String hello(){
        return "hello";
    }

    @RequestMapping("/system/menu")
    public List<SystemMenu> system(){
        log.info(SecurityUtil.getUserName()+":"+SecurityUtil.getUserId()+":"+SecurityUtil.getUserInfo());
        return userService.listUserMenus();
    }

    @RequestMapping("/system/user")
    public String systemUser(){
        return "system/user";
    }
}
