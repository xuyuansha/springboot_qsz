package com.qsz.bmss.service;

import com.github.pagehelper.PageInfo;
import com.qsz.bmss.domain.SystemRole;
import com.qsz.bmss.domain.SystemUser;
import com.qsz.bmss.model.FormUser;
import com.qsz.bmss.model.QueryParams;
import com.qsz.bmss.model.Result;
import com.qsz.bmss.model.User;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.UnknownServiceException;
import java.util.List;

public interface ISystemUserService {
    SystemUser selectUserByUserName(String userName);

    List<SystemRole> selectSysRoleByUserId(Integer userId);

    SystemUser selectUserWithRole();

    PageInfo<User> selectAllUser(Integer pageNo, Integer pageSize,QueryParams queryParams);

    Result updateUser(FormUser user);

    Result updateStatusById(Integer id, Boolean status);

    Result deleteUsers(Integer[] ids);

    String savePhoto(MultipartFile file, HttpServletRequest request) throws IOException;
}
