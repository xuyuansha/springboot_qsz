package com.qsz.bmss.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qsz.bmss.common.LogDescription;
import com.qsz.bmss.dao.SystemRoleDao;
import com.qsz.bmss.domain.SystemMenu;
import com.qsz.bmss.domain.SystemRole;
import com.qsz.bmss.domain.SystemRoleMenu;
import com.qsz.bmss.model.*;
import com.qsz.bmss.security.utils.SecurityUtil;
import com.qsz.bmss.service.ISystemLogService;
import com.qsz.bmss.service.ISystemRoleMenuService;
import com.qsz.bmss.service.ISystemRoleService;
import com.qsz.bmss.service.ISystemUserRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service("systemRoleService")
public class SystemRoleServiceImpl extends ServiceImpl<SystemRoleDao, SystemRole> implements ISystemRoleService {

    @Autowired
    ISystemRoleMenuService systemRoleMenuService;
    @Autowired
    ISystemLogService systemLogService;
    @Autowired
    ISystemUserRoleService systemUserRoleService;

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
            //删除角色菜单表的记录
            systemRoleMenuService.deleteByRoleIds(ids);
            //删除用户角色表的记录
            systemUserRoleService.deleteByRoleIds(ids);

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


    @Override
    public Result updateRole(FormRole role) {
        SystemRole systemRole = new SystemRole();
        BeanUtils.copyProperties(role,systemRole);
        if (role.getRoleId() == null){
            //新建
            systemRole.setAddTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            systemRole.setCreateUserId(SecurityUtil.getUserId());
            systemRole.setRoleLevel((short)2);

            boolean success = false ;
            try {
                int ret = this.baseMapper.insert(systemRole);
                if (ret == -1)
                    return ResultGenerator.genFailResult(ResultCode.USER_INSERT_ERROR, "角色信息保存失败");

                boolean ret2 = saveMenus(systemRole, role);
                if (!ret2) {
                    return ResultGenerator.genFailResult(ResultCode.USER_ROlE_INSERT_ERROR, "角色信息保存失败");
                } else {
                    success = true;
                    return ResultGenerator.genSuccessResult();
                }
            }catch (Exception e){
                return ResultGenerator.genFailResult(ResultCode.USER_INSERT_ERROR, "角色信息保存失败");
            }finally {
                systemLogService.log(LogDescription.S_ADD_ROLE, success,
                        LogDescription.D_ADD_ROLE, role.getRoleName());
            }


        }else {
            boolean success = false;
            try {
                boolean ret = updateById(systemRole);
                if (!ret) {
                    return ResultGenerator.genFailResult(ResultCode.USER_ROlE_INSERT_ERROR, "用户信息保存失败");
                }

                //先删除
                systemRoleMenuService.deleteByRoleId(role.getRoleId());

                boolean ret2 = saveMenus(systemRole, role);
                if (!ret2) {
                    return ResultGenerator.genFailResult(ResultCode.USER_ROlE_INSERT_ERROR, "用户角色信息保存失败");
                } else {
                    success = true;
                    return ResultGenerator.genSuccessResult();
                }
            } catch (Exception e) {
                return ResultGenerator.genFailResult(ResultCode.USER_INSERT_ERROR, "用户信息保存失败");
            } finally {
                systemLogService.log(LogDescription.S_MODIFY_USER, success,
                        LogDescription.D_MODIFY_USER, role.getRoleName());
            }
        }

    }

    private boolean saveMenus(SystemRole systemRole, FormRole role) {
        List<SystemRoleMenu> systemRoleMenus = new ArrayList();
        Integer[] menus = role.getMenus();
        for (Integer menuId: menus) {
            SystemRoleMenu systemRoleMenu = new SystemRoleMenu( menuId,systemRole.getRoleId());
            systemRoleMenus.add(systemRoleMenu);
        }

        boolean ret2 = systemRoleMenuService.insertList(systemRoleMenus);
        return ret2;
    }
}
