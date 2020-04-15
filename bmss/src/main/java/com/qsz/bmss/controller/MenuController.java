package com.qsz.bmss.controller;

import com.qsz.bmss.config.ServiceException;
import com.qsz.bmss.domain.SystemMenu;
import com.qsz.bmss.model.Menu;
import com.qsz.bmss.model.Result;
import com.qsz.bmss.model.ResultGenerator;
import com.qsz.bmss.service.ISystemMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class MenuController {
    @Autowired
    ISystemMenuService systemMenuService;

    /**
     * 根据Spring Security 中的token查询用户可以操作的菜单
     */
    @GetMapping("/user/menus")
    public Result menus() throws ServiceException {
//        throw new ServiceException("服务端异常！");
//        return ResultGenerator.genSuccessResult();
        List<Menu> menus = systemMenuService.selectSystemMenusByToken();
        return ResultGenerator.genSuccessResult(menus);
    }


}
