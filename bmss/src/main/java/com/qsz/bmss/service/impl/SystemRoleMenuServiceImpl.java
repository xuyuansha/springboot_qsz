package com.qsz.bmss.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qsz.bmss.dao.SystemRoleMenuDao;
import com.qsz.bmss.dao.SystemUserRoleDao;
import com.qsz.bmss.domain.SystemRoleMenu;
import com.qsz.bmss.domain.SystemUserRole;
import com.qsz.bmss.service.ISystemRoleMenuService;
import com.qsz.bmss.service.ISystemUserRoleService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sherry.xu
 * @Date 2020/6/24 16:17
 */
@Service("systemRoleMenuService")
public class SystemRoleMenuServiceImpl extends ServiceImpl<SystemRoleMenuDao, SystemRoleMenu> implements ISystemRoleMenuService {


    @Override
    public boolean insertList(List<SystemRoleMenu> systemRoleMenuList) {
       return saveBatch(systemRoleMenuList);
    }

    @Override
    public void deleteByRoleId(Integer roleId) {
        QueryWrapper<SystemRoleMenu> wrapper = new QueryWrapper<SystemRoleMenu>();
        wrapper.eq("role_id", roleId);
        remove(wrapper);
    }

    @Override
    public boolean deleteByRoleIds(Integer[] ids) {
//        QueryWrapper<SystemUserRole> wrapper = new QueryWrapper<SystemUserRole>();
        Map<String, Object> map = new HashMap<>();
        for (Integer roleId: ids) {
            map.put("role_id", roleId);
        }
        return  removeByMap(map);

    }
}
