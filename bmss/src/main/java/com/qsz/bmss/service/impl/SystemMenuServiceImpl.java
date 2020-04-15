package com.qsz.bmss.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qsz.bmss.dao.SystemMenuDao;
import com.qsz.bmss.domain.SystemMenu;
import com.qsz.bmss.domain.SystemUser;
import com.qsz.bmss.model.Menu;
import com.qsz.bmss.security.utils.SecurityUtil;
import com.qsz.bmss.service.ISystemMenuService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("systemMenuService")
public class SystemMenuServiceImpl extends ServiceImpl<SystemMenuDao, SystemMenu>implements ISystemMenuService {
    public List<SystemMenu> selectAllSystemMenus(){
        return this.baseMapper.selectList(null);
    }


//    List<Menu> menus = userService.findMenuIdsByName(currentName, 1, 0);
    @Override
    public List<Menu> selectSystemMenusByToken() {
       String  userName = SecurityUtil.getUserName();
        if (userName.equals("admin")) {
            List<SystemMenu> list = selectAllSystemMenus();
            return convertMenu(list, 1L, 0L);
        }



        List<SystemMenu> menuIdList = this.baseMapper.findMenuIdsByName(userName);
        Set<Long> menuIds = new HashSet<Long>();
        for (SystemMenu sm : menuIdList) {
            menuIds.add(sm.getMenuId());
            if (sm.getParentMenuId() != 0) {
                menuIds.add(sm.getParentMenuId());
            }
        }

        List<Long> ids = new ArrayList<Long>(menuIds);
        List<SystemMenu> menuList = this.baseMapper.selectBatchIds(ids);
        //System.out.println("before : " + new Gson().toJson(menuList));

        return convertMenu(menuList, 0L, 1L);
    }

    private List<Menu> convertMenu(List<SystemMenu> list,Long parentId, Long childId) {
        Map<Long, Menu> menuMaps = new HashMap<Long, Menu>();
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
