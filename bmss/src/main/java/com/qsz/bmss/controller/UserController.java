package com.qsz.bmss.controller;

import com.qsz.bmss.model.*;
import com.qsz.bmss.service.ISystemUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author sherry.xu
 * @Date 2020/4/23 14:56
 */
@Slf4j
@RestController
public class UserController {
    @Autowired
    ISystemUserService userService;

    /**
     * 查询用户
     * @param pageNo
     * @param pageSize
     * @param params
     * @return
     */
    @GetMapping(value="/system/user/v1", produces="application/json;charset=UTF-8")
    public Result users(@RequestParam(required = false) Integer pageNo, @RequestParam(required = false) Integer pageSize,  QueryParams params){
        return ResultGenerator.genSuccessResult(userService.selectAllUser(pageNo,pageSize,params));
    }

    /**
     * 添加用户和更新
     * @return
     */
    @PostMapping(value = "/system/user/v1", produces = "application/json;charset=UTF-8")
    public Result updateUser(@RequestBody FormUser user){
        //判断user是否有userId, 有就是更新，没有就是新增
        return userService.updateUser(user);
    }

    /**
     * 启用和禁用
     */
    @PutMapping(value = "/system/user/updateStatus/v1", produces = "application/json;charset=UTF-8")
    public Result addUser(Integer id, Boolean status){
        log.info(""+id+":"+status);

        return userService.updateStatusById(id, status);
    }

    /**
     * 删除用户
     * @return
     */
    @DeleteMapping(value = "/system/user/v1/{ids}", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Result delUser(@PathVariable  Integer[] ids){
        return userService.deleteUsers(ids);
    }

    @PostMapping(value="/system/userPhoto/upload/v1")
    public Result uploadUserPhoto(@RequestParam("file")MultipartFile file, HttpServletRequest request)  throws IOException {
//        response.setHeader("Access-Control-Allow-Origin", "*");
        //todo:接收文件， 返回文件链接
        String url = userService.savePhoto(file, request);

        return ResultGenerator.genSuccessResult(url);
    }
}
