package com.qsz.bmss.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qsz.bmss.dao.SystemMenuDao;
import com.qsz.bmss.domain.SystemMenu;
import com.qsz.bmss.domain.SystemUser;
import com.qsz.bmss.security.utils.SecurityUtil;
import com.qsz.bmss.service.ISystemMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("systemMenuService")
public class SystemMenuServiceImpl extends ServiceImpl<SystemMenuDao, SystemMenu>implements ISystemMenuService {
    public List<SystemMenu> selectAllSystemMenus(){
        return this.baseMapper.selectList(null);
    }

    @Override
    public List<SystemMenu> selectSystemMenusByToken() {
        Long userId = SecurityUtil.getUserId();
        if (userId == 1){
            return selectAllSystemMenus();
        }else {
            return null;
        }


    }
}
