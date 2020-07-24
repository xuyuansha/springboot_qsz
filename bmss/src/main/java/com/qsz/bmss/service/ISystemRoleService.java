package com.qsz.bmss.service;

import com.github.pagehelper.PageInfo;
import com.qsz.bmss.domain.SystemRole;
import com.qsz.bmss.model.*;

import java.util.List;

public interface ISystemRoleService {
    List<SystemRole> selectRolesByMenuId(Integer menuId);

    List<SystemRole> getAllRoles();

    PageInfo<Role> selectAllRoles(Integer pageNo, Integer pageSize, QueryParams params);

    Result deleteRoles(Integer[] ids);

    Result updateRole(FormRole role);
}
