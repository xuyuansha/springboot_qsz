package com.qsz.bmss.service.impl;
import com.mysql.cj.log.Log;
import com.qsz.bmss.dao.ISystemMenuDao;
import com.qsz.bmss.dao.ISystemRoleDao;
import com.qsz.bmss.dao.ISystemUserDao;
import com.qsz.bmss.domain.SystemMenu;
import com.qsz.bmss.domain.SystemRole;
import com.qsz.bmss.domain.SystemUser;
import com.qsz.bmss.service.IUserService;
import com.qsz.bmss.utils.ResultUtil;
import com.qsz.bmss.utils.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service("userService")
public class UserService implements IUserService {
    @Autowired
    ISystemUserDao systemUserDao;
    @Autowired
    ISystemMenuDao menuDao;

    @Override
    public SystemUser findUserByUserName(String userName) {
        return systemUserDao.findUserByUserName(userName);
    }

    @Override
    public  List<SystemMenu>  listUserMenus() {
        List<SystemMenu> menuByRoleList = menuDao.findMenuByUserId(SecurityUtil.getUserId());
        log.info("--->"+menuByRoleList);


        return menuByRoleList;

    }


}
