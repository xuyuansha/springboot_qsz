package com.qsz.bmss.service;

import com.qsz.bmss.domain.SystemMenu;
import com.qsz.bmss.model.Menu;

import java.util.List;

public interface ISystemMenuService {
    public List<SystemMenu> selectAllSystemMenus();

    List<Menu> selectSystemMenusByToken();
}
