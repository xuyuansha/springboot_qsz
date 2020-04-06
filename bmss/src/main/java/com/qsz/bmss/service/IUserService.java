package com.qsz.bmss.service;

import com.qsz.bmss.domain.SystemMenu;
import com.qsz.bmss.domain.SystemRole;
import com.qsz.bmss.domain.SystemUser;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Map;

public interface IUserService {
    SystemUser findUserByUserName(String userName);

    List<SystemMenu> listUserMenus();
}
