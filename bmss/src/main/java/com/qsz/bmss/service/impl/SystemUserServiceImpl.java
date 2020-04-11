package com.qsz.bmss.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qsz.bmss.dao.SystemUserDao;
import com.qsz.bmss.domain.SystemRole;
import com.qsz.bmss.domain.SystemUser;
import com.qsz.bmss.service.ISystemUserService;
import lombok.extern.slf4j.Slf4j;
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
}
