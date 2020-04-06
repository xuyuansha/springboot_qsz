package com.qsz.bmss.service;

import com.qsz.bmss.domain.SystemMenu;

import java.util.List;

public interface ISystemMenuService {
    List<SystemMenu> getAllMenuWithRole();

    List<SystemMenu> selectSysMenuByUserId(Integer userId);

    /*List<SystemMenu> getMenuByUserId(Integer userId);*/
}
