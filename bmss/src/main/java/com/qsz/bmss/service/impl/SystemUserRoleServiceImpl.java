package com.qsz.bmss.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qsz.bmss.dao.SystemUserRoleDao;
import com.qsz.bmss.domain.SystemUserRole;
import com.qsz.bmss.service.ISystemUserRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author sherry.xu
 * @Date 2020/6/24 16:17
 */
@Service("systemUserRoleService")
public class SystemUserRoleServiceImpl extends ServiceImpl<SystemUserRoleDao, SystemUserRole> implements ISystemUserRoleService {


    @Override
    public boolean insertList(List<SystemUserRole> sysUserRoleList) {
       return saveOrUpdateBatch(sysUserRoleList);
    }
}
