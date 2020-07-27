package com.qsz.bmss.service;

import com.qsz.bmss.domain.SystemRoleMenu;

import java.util.List;

/**
 * @author sherry.xu
 * @Date 2020/7/22 17:06
 */
public interface ISystemRoleMenuService {
    boolean insertList(List<SystemRoleMenu> sysUserRoleList);

    void deleteByRoleId(Integer roleId);

    boolean deleteByRoleIds(Integer[] ids);

    boolean deleteByMenuIds(Integer[] ids);
}
