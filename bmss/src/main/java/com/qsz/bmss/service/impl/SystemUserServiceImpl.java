package com.qsz.bmss.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qsz.bmss.dao.SystemUserDao;
import com.qsz.bmss.domain.SystemRole;
import com.qsz.bmss.domain.SystemUser;
import com.qsz.bmss.model.QueryParams;
import com.qsz.bmss.model.SelfUser;
import com.qsz.bmss.model.User;
import com.qsz.bmss.security.utils.SecurityUtil;
import com.qsz.bmss.service.ISystemUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service("systemUserService")
@Slf4j
public class SystemUserServiceImpl extends ServiceImpl<SystemUserDao,SystemUser> implements ISystemUserService {

    @Override
    public SystemUser selectUserByUserName(String userName) {
        QueryWrapper<SystemUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SystemUser::getUsername,userName);
        return this.baseMapper.selectOne(queryWrapper);
    }

    @Override
    public List<SystemRole> selectSysRoleByUserId(Long userId) {
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


}
