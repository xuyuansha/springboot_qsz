package com.qsz.bmss.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qsz.bmss.domain.SystemMenu;
import com.qsz.bmss.domain.SystemRole;
import com.qsz.bmss.domain.SystemUser;
import com.qsz.bmss.model.QueryParams;
import com.qsz.bmss.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SystemUserDao extends BaseMapper<SystemUser> {
    List<SystemRole> selectSysRoleByUserId(Long userId);

    List<User> getAllUsersWithRoles(QueryParams params);
//    List<SystemMenu> selectSystemMenuByUserId(Long userId);
}

