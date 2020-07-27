package com.qsz.bmss.service;

import com.github.pagehelper.PageInfo;
import com.qsz.bmss.domain.SystemMenu;
import com.qsz.bmss.model.*;

import java.util.List;

public interface ISystemMenuService {
    List<Menu> getAllMenus();

    List<SystemMenu> selectAllSystemMenus();

    List<Menu> selectSystemMenusByToken();

    PageInfo<Menu> selectAllMenus(Integer pageNo, Integer pageSize, QueryParams params);

    List<SystemMenu> selectOnLevelMenus();

    Result updateMenu(FormMenu menu);

    Result deleteMenus(Integer[] ids);
}
