package com.qsz.bmss.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qsz.bmss.common.LogDescription;
import com.qsz.bmss.dao.SystemMenuDao;
import com.qsz.bmss.domain.SystemMenu;
import com.qsz.bmss.domain.SystemRole;
import com.qsz.bmss.domain.SystemUser;
import com.qsz.bmss.model.*;
import com.qsz.bmss.security.utils.SecurityUtil;
import com.qsz.bmss.service.ISystemLogService;
import com.qsz.bmss.service.ISystemMenuService;
import com.qsz.bmss.service.ISystemRoleMenuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service("systemMenuService")
public class SystemMenuServiceImpl extends ServiceImpl<SystemMenuDao, SystemMenu>implements ISystemMenuService {
    @Autowired
    ISystemLogService systemLogService;
    @Autowired
    ISystemRoleMenuService systemRoleMenuService;

    public List<SystemMenu> selectAllSystemMenus(){
        return selectAllSystemMenus(null);
    }

    public List<SystemMenu> selectAllSystemMenus(QueryParams params){
        if (params == null || StringUtils.isEmpty(params.getKeyword())) {
            return this.baseMapper.selectList(null);
        }else {
            QueryWrapper<SystemMenu> wrapper = new QueryWrapper<SystemMenu>();
            wrapper.like("menu_name", params.getKeyword());
            return this.baseMapper.selectList(wrapper);
        }
    }

    @Override
    public List<Menu> getAllMenus() {
        return getAllMenus(null);
    }


    public List<Menu> getAllMenus(QueryParams params) {
        List<SystemMenu> list = selectAllSystemMenus(params);
        Map<Integer, Menu> menuMaps = new HashMap<Integer, Menu>();
        for (SystemMenu sm : list) {
            if (sm.getParentMenuId() == 0) {
                Menu m = new Menu();
                BeanUtils.copyProperties(sm, m);
                menuMaps.put(sm.getMenuId(), m);
            }
        }
        for (SystemMenu sm : list) {
            if (sm.getParentMenuId() != 0) {
                Menu m = menuMaps.get(sm.getParentMenuId());
                List<Menu> subMenus = m.getSubMenu();
                if (subMenus == null) {
                    subMenus = new ArrayList<Menu>();
                }

                Menu sub = new Menu();
                BeanUtils.copyProperties(sm, sub);
                subMenus.add(sub);

                m.setSubMenu(subMenus);
                menuMaps.put(m.getMenuId(), m);
            }
        }
        List<Menu> menusList = new ArrayList<Menu>(menuMaps.values());
        sort(menusList);

        return menusList;
    }


//    List<Menu> menus = userService.findMenuIdsByName(currentName, 1, 0);
    @Override
    public List<Menu> selectSystemMenusByToken() {
       String  userName = SecurityUtil.getUserName();
        if (userName.equals("admin")) {
            List<SystemMenu> list = selectAllSystemMenus();
            return convertMenu(list, 1L, 0);
        }

        List<SystemMenu> menuIdList = this.baseMapper.findMenuIdsByName(userName);
        Set<Integer> menuIds = new HashSet<Integer>();
        for (SystemMenu sm : menuIdList) {
            menuIds.add(sm.getMenuId());
            if (sm.getParentMenuId() != 0) {
                menuIds.add(sm.getParentMenuId());
            }
        }

        List<Integer> ids = new ArrayList<Integer>(menuIds);
        if (ids==null || ids.size()==0)
            return null;
        List<SystemMenu> menuList = this.baseMapper.selectBatchIds(ids);
        //System.out.println("before : " + new Gson().toJson(menuList));

        return convertMenu(menuList, 0L, 1);
    }

    @Override
    public PageInfo<Menu> selectAllMenus(Integer pageNo, Integer pageSize, QueryParams params) {
        PageHelper.startPage(pageNo==null?1:pageNo, pageSize==null?10:pageSize);
        List<Menu> allMenus = getAllMenus(params);

        return new PageInfo<>(allMenus);
    }

    @Override
    public List<SystemMenu> selectOnLevelMenus() {
        QueryWrapper<SystemMenu> wrapper = new QueryWrapper<SystemMenu>();
        wrapper.eq("parent_menu_id", 0);
        return this.baseMapper.selectList(wrapper);
    }

    @Override
    public Result updateMenu(FormMenu menu) {
        SystemMenu systemMenu = new SystemMenu();
        BeanUtils.copyProperties(menu,systemMenu);
        boolean success = false;
        if (menu.getMenuId() == null){
            //新建
//            systemMenu.setAddTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            try {
                int ret = this.baseMapper.insert(systemMenu);
                if (ret == -1)
                    return ResultGenerator.genFailResult(ResultCode.USER_INSERT_ERROR, "菜单信息保存失败");
                else {
                    success = true;
                    return ResultGenerator.genSuccessResult();
                }

            }catch (Exception e){
                return ResultGenerator.genFailResult(ResultCode.USER_INSERT_ERROR, "菜单信息保存失败");
            }finally {
                systemLogService.log(LogDescription.S_ADD_MENU, success,
                        LogDescription.D_ADD_MENU, menu.getMenuName());
            }
        }else {
            SystemMenu oldMenu = getById(systemMenu.getMenuId());
            try {
                boolean ret = updateById(systemMenu);
                if (!ret) {
                    return ResultGenerator.genFailResult(ResultCode.USER_ROlE_INSERT_ERROR, "菜单信息保存失败");
                }else {
                    success = true;
                    return ResultGenerator.genSuccessResult();
                }

            } catch (Exception e) {
                return ResultGenerator.genFailResult(ResultCode.USER_INSERT_ERROR, "菜单信息保存失败");
            } finally {
                systemLogService.log(LogDescription.S_MODIFY_MENU, success,
                        LogDescription.D_MODIFY_MENU,oldMenu.getMenuName(), menu.getMenuName());
            }
        }

    }

    @Override
    public Result deleteMenus(Integer[] ids) {
            Collection<SystemMenu> systemMenus = listByIds(Arrays.asList(ids));
            boolean success = false;
            try {
                //删除角色菜单表的记录
                systemRoleMenuService.deleteByMenuIds(ids);
                boolean ret = removeByIds(Arrays.asList(ids));
                if (ret) {
                    success = true;
                    return ResultGenerator.genSuccessResult();
                }
                else
                    return ResultGenerator.genFailResult(ResultCode.ROLE_DELETE_ERROR, "菜单删除失败");
            }catch (Exception e){
                return ResultGenerator.genFailResult(ResultCode.ROLE_DELETE_ERROR, "菜单删除失败");
            }finally {
                ArrayList<String> menus = new ArrayList();
                for (SystemMenu menu: systemMenus ) {
                    menus.add(menu.getMenuName());
                }
                systemLogService.log(LogDescription.S_DELETE_MENU, success,
                        LogDescription.D_DELETE_MENU, StringUtils.join(menus, ','));
            }
    }

    private List<Menu> convertMenu(List<SystemMenu> list,Long parentId, Integer childId) {
        Map<Integer, Menu> menuMaps = new HashMap<Integer, Menu>();
        for (SystemMenu sm : list) {
            if (sm.getParentMenuId() == 0) {
                Menu m = new Menu();
                BeanUtils.copyProperties(sm, m);
                menuMaps.put(sm.getMenuId(), m);
            }
        }

        for (SystemMenu sm : list) {
            if (sm.getParentMenuId() != 0) {
                Menu m = menuMaps.get(sm.getParentMenuId());
                List<Menu> subMenus = m.getSubMenu();
                if (subMenus == null) {
                    subMenus = new ArrayList<Menu>();
                }

                Menu sub = new Menu();
                BeanUtils.copyProperties(sm, sub);
                if (sm.getMenuId() == childId) {
                    sub.setActive(true);
                }
                subMenus.add(sub);

                m.setSubMenu(subMenus);
                menuMaps.put(m.getMenuId(), m);
            }
        }
        List<Menu> menusList = new ArrayList<Menu>(menuMaps.values());
        sort(menusList);

        return menusList;
    }

    private void sort(List<Menu> list) {
        List<Menu> menus = list;
        MenuComparator comparator = new MenuComparator();
        Collections.sort(menus, comparator);

        for (Menu m : menus) {
            List<Menu> subMenus = m.getSubMenu();
            if (subMenus != null) {
                Collections.sort(subMenus, comparator);
            }
        }
    }

    private class MenuComparator implements Comparator<Menu> {

        @Override
        public int compare(Menu o1, Menu o2) {
            return o1.getMenuNumber() - o2.getMenuNumber();
        }

    }

}
