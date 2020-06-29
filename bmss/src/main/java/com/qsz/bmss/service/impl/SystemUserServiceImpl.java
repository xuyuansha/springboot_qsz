package com.qsz.bmss.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qsz.bmss.annotation.OperationLogDetail;
import com.qsz.bmss.annotation.OperationType;
import com.qsz.bmss.annotation.OperationUnit;
import com.qsz.bmss.dao.SystemUserDao;
import com.qsz.bmss.domain.SystemRole;
import com.qsz.bmss.domain.SystemUser;
import com.qsz.bmss.domain.SystemUserRole;
import com.qsz.bmss.model.*;
import com.qsz.bmss.security.utils.SecurityUtil;
import com.qsz.bmss.service.ISystemUserRoleService;
import com.qsz.bmss.service.ISystemUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service("systemUserService")
@Transactional
@Slf4j
public class SystemUserServiceImpl extends ServiceImpl<SystemUserDao,SystemUser> implements ISystemUserService {

    @Autowired
    ISystemUserRoleService systemUserRoleService;

    @Override
    public SystemUser selectUserByUserName(String userName) {
        QueryWrapper<SystemUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SystemUser::getUsername,userName);
        return this.baseMapper.selectOne(queryWrapper);
    }

    @Override
    public List<SystemRole> selectSysRoleByUserId(Integer userId) {
        return this.baseMapper.selectSysRoleByUserId(userId);
    }

    @Override
    public SystemUser selectUserWithRole() {
        SelfUser selfUser = SecurityUtil.getUserInfo();

        SystemUser systemUser = selectUserByUserName(selfUser.getUsername());
        systemUser.setPassword("");
        systemUser.setRoles(selectSysRoleByUserId(systemUser.getUserId()));

        return systemUser;
    }

    @Override
    public PageInfo<User> selectAllUser(Integer pageNo, Integer pageSize, QueryParams params){
        PageHelper.startPage(pageNo==null?1:pageNo, pageSize==null?10:pageSize);
        List<User> list = this.baseMapper.getAllUsersWithRoles(params);
       return new PageInfo<>(list);
    }

    @OperationLogDetail(detail = "更新用户[{{user}}]信息", level = 1, operationUnit = OperationUnit.USER, operationType = OperationType.UPDATE)
    @Override
    public Result updateUser(FormUser user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (user.getUserId() == null){
            //新建
            SystemUser systemUser = new SystemUser();
            BeanUtils.copyProperties(user,systemUser);

            systemUser.setPassword(encoder.encode(systemUser.getPassword()));
            systemUser.setAddTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            int ret = this.baseMapper.insert(systemUser);

            if (ret == -1)
                return ResultGenerator.genFailResult(ResultCode.USER_INSERT_ERROR,"用户信息保存失败");

            boolean ret2 = saveRoles(systemUser,user);
            if (!ret2){
                return ResultGenerator.genFailResult(ResultCode.USER_ROlE_INSERT_ERROR,"用户角色信息保存失败");
            }else {
                return ResultGenerator.genSuccessResult();
            }

        }else {
            //更新
            SystemUser systemUser = new SystemUser();
            BeanUtils.copyProperties(user,systemUser);
            if (!StringUtils.isEmpty(systemUser.getPassword())){
                systemUser.setPassword(encoder.encode(systemUser.getPassword()));
            }
            boolean ret = updateById(systemUser);
            if(!ret ){
                return ResultGenerator.genFailResult(ResultCode.USER_ROlE_INSERT_ERROR,"用户信息保存失败");
            }
            //先删除
            systemUserRoleService.deleteByUserId(systemUser.getUserId());

            boolean ret2 = saveRoles(systemUser,user);
            if (!ret2){
                return ResultGenerator.genFailResult(ResultCode.USER_ROlE_INSERT_ERROR,"用户角色信息保存失败");
            }else {
                return ResultGenerator.genSuccessResult();
            }
        }

    }

    @Override
    public Result updateStatusById(Integer id, Boolean status) {
        UpdateWrapper<SystemUser> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("user_id", id).set("user_status",status?1:0);
        boolean ret = update(updateWrapper);
        if (ret)
            return ResultGenerator.genSuccessResult();
        else
            return  ResultGenerator.genFailResult(ResultCode.USER_STATUS_SET_ERROR,"用户状态设置失败");
    }

    @Override
    public Result deleteUsers(Integer[] ids) {
        systemUserRoleService.deleteByUserIds(ids);
        boolean ret = removeByIds(Arrays.asList(ids));
        if (ret)
            return ResultGenerator.genSuccessResult();
        else
            return  ResultGenerator.genFailResult(ResultCode.USER_DELETE_ERROR,"用户删除失败");
    }

    private boolean saveRoles(SystemUser systemUser,FormUser user) {
        List<SystemUserRole> sysUserRoleList = new ArrayList();
        Integer[] roles = user.getRoles();
        for (Integer roleId: roles) {
            SystemUserRole systemUserRole = new SystemUserRole(systemUser.getUserId(), roleId);
            sysUserRoleList.add(systemUserRole);
        }

        boolean ret2 = systemUserRoleService.insertList(sysUserRoleList);
        return ret2;
    }


}
