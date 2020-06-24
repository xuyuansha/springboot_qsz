package com.qsz.bmss.service;

import com.qsz.bmss.domain.SystemRole;

import java.util.List;

public interface ISystemRoleService {
    List<SystemRole> selectRolesByMenuId(Integer menuId);

    List<SystemRole> getAllRoles();
}
