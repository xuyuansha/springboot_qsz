package com.qsz.bmss.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qsz.bmss.dao.SystemUserRoleDao;
import com.qsz.bmss.domain.SystemUserRole;
import com.qsz.bmss.service.ISystemUserRoleService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sherry.xu
 * @Date 2020/6/24 16:17
 */
@Service("systemUserRoleService")
public class SystemUserRoleServiceImpl extends ServiceImpl<SystemUserRoleDao, SystemUserRole> implements ISystemUserRoleService {


    @Override
    public boolean insertList(List<SystemUserRole> sysUserRoleList) {
       return saveBatch(sysUserRoleList);
    }

    @Override
    public void deleteByUserId(Integer userId) {
        QueryWrapper<SystemUserRole> wrapper = new QueryWrapper<SystemUserRole>();
        wrapper.eq("user_id", userId);
        remove(wrapper);
    }

    @Override
    public boolean deleteByUserIds(Integer[] userIds) {
//        QueryWrapper<SystemUserRole> wrapper = new QueryWrapper<SystemUserRole>();
        Map<String, Object> map = new HashMap<>();
        for (Integer userId: userIds) {
            map.put("user_id", userId);
        }
        return  removeByMap(map);

    }

    @Override
    public boolean deleteByRoleIds(Integer[] ids) {
        Map<String, Object> map = new HashMap<>();
        for (Integer id: ids) {
            map.put("role_id", id);
        }
        return  removeByMap(map);

    }
}
