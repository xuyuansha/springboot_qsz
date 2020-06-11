package com.qsz.bmss.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qsz.bmss.dao.SystemRoleDao;
import com.qsz.bmss.domain.SystemRole;
import com.qsz.bmss.service.ISystemRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("systemRoleService")
public class SystemRoleServiceImpl extends ServiceImpl<SystemRoleDao, SystemRole> implements ISystemRoleService {
    @Override
    public List<SystemRole> selectRolesByMenuId(Long menuId) {
        return this.baseMapper.selectRolesByMenuId(menuId);
    }

    @Override
    public List<SystemRole> getAllRoles() {
        return this.baseMapper.selectList(null);
    }
}
