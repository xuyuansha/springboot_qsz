package com.qsz.bmss.dao;

import com.qsz.bmss.domain.SystemMenu;
import com.qsz.bmss.domain.SystemRole;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ISystemMenuDao extends JpaRepository<SystemMenu,Integer> {

//    SystemMenu getMenuByMenuId(Integer menuId);
//    @Query(value = "select * from sys_menu where menu_id in (1,2,3)", nativeQuery = true)
//    List<SystemMenu> findMenuByRoleList(List<SystemRole> list);

    @Query(value = "select * from sys_menu where menu_id in (1,2,3)", nativeQuery = true)
    List<SystemMenu> findMenuByUserId(Integer userId);
}
