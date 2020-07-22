package com.qsz.bmss.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qsz.bmss.common.LogDescription;
import com.qsz.bmss.dao.SystemRoleDao;
import com.qsz.bmss.domain.SystemRole;
import com.qsz.bmss.model.*;
import com.qsz.bmss.service.ISystemLogService;
import com.qsz.bmss.service.ISystemRoleMenuService;
import com.qsz.bmss.service.ISystemRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Service("systemRoleService")
public class SystemRoleServiceImpl extends ServiceImpl<SystemRoleDao, SystemRole> implements ISystemRoleService {
    @Autowired
    ISystemRoleMenuService systemRoleMenuService;
    @Autowired
    ISystemLogService systemLogService;

    @Override
    public List<SystemRole> selectRolesByMenuId(Integer menuId) {
        return this.baseMapper.selectRolesByMenuId(menuId);
    }

    @Override
    public List<SystemRole> getAllRoles() {
        return this.baseMapper.selectList(null);
    }

    @Override
    public PageInfo<Role> selectAllRoles(Integer pageNo, Integer pageSize, QueryParams params) {
        PageHelper.startPage(pageNo==null?1:pageNo, pageSize==null?10:pageSize);
      /*  QueryWrapper<SystemRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().like(SystemRole::getRoleName, params.getKeyword()).orderByAsc(SystemRole::getRoleId);
        List<SystemRole> list = this.baseMapper.selectList(queryWrapper);*/
       List<Role> list = this.baseMapper.getRolesWithMenu(params);
        return new PageInfo<>(list);
    }

    @Override
    public Result deleteRoles(Integer[] ids) {
        Collection<SystemRole> systemRoles = listByIds(Arrays.asList(ids));

        boolean success = false;
        try {
            systemRoleMenuService.deleteByRoleIds(ids);
            boolean ret = removeByIds(Arrays.asList(ids));
            if (ret) {
                success = true;
                return ResultGenerator.genSuccessResult();
            }
            else
                return ResultGenerator.genFailResult(ResultCode.ROLE_DELETE_ERROR, "角色删除失败");
        }catch (Exception e){
            return ResultGenerator.genFailResult(ResultCode.ROLE_DELETE_ERROR, "角色删除失败");
        }finally {
            ArrayList<String> roles = new ArrayList();
            for (SystemRole role: systemRoles ) {
                roles.add(role.getRoleName());
            }
            systemLogService.log(LogDescription.S_DELETE_ROLE, success,
                    LogDescription.D_DELETE_ROLE, StringUtils.join(roles, ','));
        }
    }
}
