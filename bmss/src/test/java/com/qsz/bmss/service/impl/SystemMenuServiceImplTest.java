package com.qsz.bmss.service.impl;

import com.qsz.bmss.domain.SystemMenu;
import com.qsz.bmss.service.ISystemMenuService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
public class SystemMenuServiceImplTest {
    @Autowired
    ISystemMenuService systemMenuService;
   @Test
    public void test(){
       List<SystemMenu> menus = systemMenuService.selectAllSystemMenus();
       System.out.println(menus);
   }

}
