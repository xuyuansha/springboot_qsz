package com.qsz.bmss.controller;

import com.qsz.bmss.config.ServiceException;
import com.qsz.bmss.domain.SystemMenu;
import com.qsz.bmss.model.Menu;
import com.qsz.bmss.model.QueryParams;
import com.qsz.bmss.model.Result;
import com.qsz.bmss.model.ResultGenerator;
import com.qsz.bmss.service.ISystemMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class MenuController {
    @Autowired
    ISystemMenuService systemMenuService;

    @GetMapping(value="/system/menu/v1", produces="application/json;charset=UTF-8")
    public Result menusByPage(@RequestParam(required = false) Integer pageNo, @RequestParam(required = false) Integer pageSize, QueryParams params){
        return ResultGenerator.genSuccessResult(systemMenuService.selectAllMenus(pageNo,pageSize,params));
    }

    @GetMapping("/system/menu/one")
    public Result oneMenus() throws ServiceException {
//        throw new ServiceException("服务端异常！");
//        return ResultGenerator.genSuccessResult();
        List<SystemMenu> menus = new ArrayList<>();
        SystemMenu systemMenu = new SystemMenu();
        systemMenu.setMenuId(0);
        systemMenu.setMenuName("无（一级菜单）");
        menus.add(systemMenu);
        menus.addAll(systemMenuService.selectOnLevelMenus());

        return ResultGenerator.genSuccessResult(menus);
    }


    /**
     * 根据Spring Security 中的token查询用户可以操作的菜单
     * 用于给前端返回可展示的路由
     */
    @GetMapping("/user/menus")
    public Result menus() throws ServiceException {
//        throw new ServiceException("服务端异常！");
//        return ResultGenerator.genSuccessResult();
        List<Menu> menus = systemMenuService.selectSystemMenusByToken();
        return ResultGenerator.genSuccessResult(menus);
    }

    @GetMapping("/menus/all")
    public Result allMenus() throws ServiceException {
//        throw new ServiceException("服务端异常！");
//        return ResultGenerator.genSuccessResult();
        List<Menu> menus = systemMenuService.getAllMenus();
        return ResultGenerator.genSuccessResult(menus);
    }


}
