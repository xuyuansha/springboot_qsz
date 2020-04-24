package com.qsz.bmss.service;

import com.qsz.bmss.domain.SystemRole;
import com.qsz.bmss.domain.SystemUser;
import java.util.List;

public interface ISystemUserService {
    SystemUser selectUserByUserName(String userName);

    List<SystemRole> selectSysRoleByUserId(Long userId);

    SystemUser selectUserWithRole();

    List<SystemUser> selectAllUser();
}
