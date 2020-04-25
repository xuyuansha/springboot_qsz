package com.qsz.bmss.service;

import com.github.pagehelper.PageInfo;
import com.qsz.bmss.domain.SystemRole;
import com.qsz.bmss.domain.SystemUser;
import com.qsz.bmss.model.QueryParams;
import com.qsz.bmss.model.User;

import java.net.UnknownServiceException;
import java.util.List;

public interface ISystemUserService {
    SystemUser selectUserByUserName(String userName);

    List<SystemRole> selectSysRoleByUserId(Long userId);

    SystemUser selectUserWithRole();

    PageInfo<User> selectAllUser(Integer pageNo, Integer pageSize,QueryParams queryParams);
}
