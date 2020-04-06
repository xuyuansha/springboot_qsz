package com.qsz.bmss.utils;

import com.alibaba.fastjson.JSON;
import com.qsz.bmss.config.security.JWTConfig;
import com.qsz.bmss.domain.SystemUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * JWT工具类
 * @Author Sans
 * @CreateTime 2019/10/2 7:42
 */
@Slf4j
public class JWTTokenUtil {

    /**
     * 私有化构造器
     */
    private JWTTokenUtil(){}

    /**
     * 生成Token
     * @Author Sans
     * @CreateTime 2019/10/2 12:16
     * @Param  selfUserEntity 用户安全实体
     * @Return Token
     */
    public static String createAccessToken(SystemUser user){
        // 登陆成功生成JWT
        String token = Jwts.builder()
                // 放入用户名和用户ID
                .setId(user.getUserId()+"")
                // 主题
                .setSubject(user.getUserName())
                // 签发时间
                .setIssuedAt(new Date())
                // 签发者
                .setIssuer("sans")
                // 自定义属性 放入用户拥有权限
                .claim("auth", JSON.toJSONString(user.getAuthorities()))
                // 失效时间
                .setExpiration(new Date(System.currentTimeMillis() + JWTConfig.expiration))
                // 签名算法和密钥
                .signWith(SignatureAlgorithm.HS512, JWTConfig.secret)
                .compact();
        return token;
    }
}