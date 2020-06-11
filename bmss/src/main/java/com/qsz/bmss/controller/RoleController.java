package com.qsz.bmss.controller;

import com.qsz.bmss.config.ServiceException;
import com.qsz.bmss.domain.SystemRole;
import com.qsz.bmss.model.Result;
import com.qsz.bmss.model.ResultGenerator;
import com.qsz.bmss.service.ISystemRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author sherry.xu
 * @Date 2020/6/11 10:40
 */
@Slf4j
@RestController
public class RoleController {
    @Autowired
    ISystemRoleService systemRoleService;

    /**
     * 查询所有角色
     */
    @GetMapping("/roles/all")
    public Result roles() throws ServiceException {

        List<SystemRole> roles = systemRoleService.getAllRoles();
        return ResultGenerator.genSuccessResult(roles);
    }
}
