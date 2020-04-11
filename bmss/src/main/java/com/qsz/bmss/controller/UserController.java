package com.qsz.bmss.controller;

import com.qsz.bmss.service.impl.SystemUserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UserController {
    @Autowired
    SystemUserServiceImpl systemUserServiceImpl;
    @RequestMapping("/user")
    public String user(){
        return "user hello";
    }

    @RequestMapping("/hello")
    public String hello(){
        return "hello";
    }

    @RequestMapping("/")
    public String index(){
        return "index ";
    }
    @RequestMapping("/system/menu")
    public String system(){

        return "String";
    }

    @RequestMapping("/system/user")
    public String systemUser(){
        return "system/user";
    }
}
