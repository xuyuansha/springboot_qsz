package com.qsz.bmss.dao;

import com.qsz.bmss.domain.SystemUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ISystemUserDaoTest {
    @Autowired
    ISystemUserDao userRepository;
    @Test
    public void testFindAll(){
        List<SystemUser> list = userRepository.findAll();
        for (SystemUser user : list) {
            System.out.println(user);
        }
    }

    @Test
    public void testFindByName(){
        SystemUser user = userRepository.findUserByUserName("admin");
        System.out.println(user);
        System.out.println(user.getRoleList());
    }


}