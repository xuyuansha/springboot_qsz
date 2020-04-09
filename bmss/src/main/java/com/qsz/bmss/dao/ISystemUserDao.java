package com.qsz.bmss.dao;

import com.qsz.bmss.domain.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISystemUserDao extends JpaRepository<SystemUser,Integer> {
    SystemUser findUserByUsername(String username);

}

