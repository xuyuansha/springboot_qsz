package com.qsz.bmss.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qsz.bmss.dao.SystemRoleDao;
import com.qsz.bmss.domain.SystemRole;
import com.qsz.bmss.model.QueryParams;
import com.qsz.bmss.model.User;
import com.qsz.bmss.service.ISystemRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("systemRoleService")
public class SystemRoleServiceImpl extends ServiceImpl<SystemRoleDao, SystemRole> implements ISystemRoleService {
    @Override
    public List<SystemRole> selectRolesByMenuId(Integer menuId) {
        return this.baseMapper.selectRolesByMenuId(menuId);
    }

    @Override
    public List<SystemRole> getAllRoles() {
        return this.baseMapper.selectList(null);
    }

    @Override
    public PageInfo<SystemRole> selectAllRoles(Integer pageNo, Integer pageSize, QueryParams params) {
        PageHelper.startPage(pageNo==null?1:pageNo, pageSize==null?10:pageSize);
        QueryWrapper<SystemRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().like(SystemRole::getRoleName, params.getKeyword()).orderByAsc(SystemRole::getRoleId);
        List<SystemRole> list = this.baseMapper.selectList(queryWrapper);
        return new PageInfo<>(list);
    }
}
