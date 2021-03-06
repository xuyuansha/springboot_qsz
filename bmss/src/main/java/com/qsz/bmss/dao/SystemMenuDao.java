package com.qsz.bmss.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qsz.bmss.domain.SystemMenu;
import com.qsz.bmss.domain.SystemUser;
import com.qsz.bmss.model.Menu;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SystemMenuDao extends BaseMapper<SystemMenu> {
    List<SystemMenu> findMenuIdsByName(String userName);
}
