package com.qsz.bmss.service.impl;

import com.qsz.bmss.dao.ISystemMenuDao;
import com.qsz.bmss.domain.SystemMenu;
import com.qsz.bmss.service.ISystemMenuService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Service("systemMenuService")
public class SystemMenuService  implements ISystemMenuService {
    @Autowired
    ISystemMenuDao systemMenuDao;
    @Override
    public List<SystemMenu> getAllMenuWithRole() {
        return systemMenuDao.findAll();
    }

    @Override
    public List<SystemMenu> selectSysMenuByUserId(Integer userId) {
        return systemMenuDao.findMenuByUserId(userId);
    }



   /* @Override
    public List<SystemMenu> getMenuByUserId(Integer userId) {
        return systemMenuDao.getMenuByMenuId(userId);
    }*/


}
