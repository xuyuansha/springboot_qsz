package com.qsz.bmss.service;

import com.qsz.bmss.domain.SystemMenu;

import java.util.List;

public interface ISystemMenuService {
    public List<SystemMenu> selectAllSystemMenus();

    List<SystemMenu> selectSystemMenusByToken();
}
