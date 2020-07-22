package com.qsz.bmss.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qsz.bmss.domain.SystemRole;
import com.qsz.bmss.domain.SystemUser;
import com.qsz.bmss.model.QueryParams;
import com.qsz.bmss.model.Role;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SystemRoleDao extends BaseMapper<SystemRole> {
    List<SystemRole> selectRolesByMenuId(Integer menuId);

    List<Role> getRolesWithMenu(QueryParams params);
}
