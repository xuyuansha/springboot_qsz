package com.qsz.bmss.service;

import com.qsz.bmss.domain.SystemUserRole;

import java.util.List;

/**
 * @author sherry.xu
 * @Date 2020/6/24 16:18
 */
public interface ISystemUserRoleService {
    boolean insertList(List<SystemUserRole> sysUserRoleList);

    void deleteByUserId(Integer userId);

    boolean deleteByUserIds(Integer[] ids);
}
