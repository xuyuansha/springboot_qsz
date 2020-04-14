package com.qsz.bmss.controller;

import com.github.pagehelper.PageHelper;
import com.qsz.bmss.config.ServiceException;
import com.qsz.bmss.domain.SystemUser;
import com.qsz.bmss.model.Result;
import com.qsz.bmss.model.ResultGenerator;
import com.qsz.bmss.service.ISystemUserService;
import com.qsz.bmss.service.impl.SystemUserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.rowset.serial.SerialException;

@Slf4j
@RestController
public class UserController {
    @Autowired
    ISystemUserService systemUserService;

    /**
     * 根据Spring Security 中的token查询用户信息，
     * @return
     * {
     *     roles: ['admin'],
     *     introduction: 'I am a super administrator',
     *     avatar: 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif',
     *     name: 'Super Admin'
     * }
     *
     * {
     *         "userId": 1,
     *         "username": "admin",
     *         "password": "",
     *         "nickName": "超级管理员",
     *         "userPhoto": "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif",
     *         "enabled": true,
     *         "roles": [
     *             {
     *                 "roleId": 1,
     *                 "createUserId": 1,
     *                 "roleName": "admin",
     *                 "roleDesc": "",
     *                 "roleLevel": 2
     *             }
     *         ]
     *     }
     */
    @RequestMapping("/user/info")
    public Result user() throws ServiceException {
//        throw new ServiceException("服务端异常！");
//        return ResultGenerator.genSuccessResult();
        SystemUser systemUser = systemUserService.selectUserWithRole();
        return ResultGenerator.genSuccessResult(systemUser);
    }


}
