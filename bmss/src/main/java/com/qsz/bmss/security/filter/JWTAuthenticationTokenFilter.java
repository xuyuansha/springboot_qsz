package com.qsz.bmss.security.filter;

import com.alibaba.fastjson.JSONObject;
import com.qsz.bmss.model.SelfUser;
import com.qsz.bmss.security.config.JWTConfig;
import com.qsz.bmss.security.utils.ResultUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class JWTAuthenticationTokenFilter extends BasicAuthenticationFilter {
    public JWTAuthenticationTokenFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String tokenHeader = request.getHeader(JWTConfig.tokenHeader);
        if (null != tokenHeader && tokenHeader.startsWith(JWTConfig.tokenPrefix)){
            try {
                String token = tokenHeader.replace(JWTConfig.tokenPrefix,"");

                log.info(token);
                Claims claims = Jwts.parser().setSigningKey(JWTConfig.secret).parseClaimsJws(token).getBody();
                String userName = claims.getSubject();
                String userId = claims.getId();
                logger.info(userId+":"+userName);
                if (!StringUtils.isEmpty(userName) && !StringUtils.isEmpty(userId)){
                    List<GrantedAuthority> authorities = new ArrayList<>();
                    String authority = claims.get("auth").toString();
                    logger.info(authority);
                    if (!StringUtils.isEmpty(authority)){
                        List<Map<String,String>> authorityMap = JSONObject.parseObject(authority,List.class);
                        for (Map<String,String> role:authorityMap) {
                            if (!StringUtils.isEmpty(role))
                                authorities.add(new SimpleGrantedAuthority(role.get("authority")));
                        }
                    }
                    SelfUser systemUser = new SelfUser();
                    systemUser.setUsername(userName);
                    systemUser.setUserId(Long.parseLong(userId));
                    systemUser.setAuthorities(authorities);

                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(systemUser,userId,authorities);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }catch (ExpiredJwtException e){
                log.error("token过期",e.getMessage());
//                throw new AccessDeniedException("token 过期");
                ResultUtil.responseJson(response, ResultUtil.resultCode(407,"token过期"));
                return;
            }catch (Exception e){
                log.error("token无效",e.getMessage());
                ResultUtil.responseJson(response,ResultUtil.resultCode(406,"token无效"));
                return;
//                throw new AccessDeniedException("token 无效");
            }
        }
            chain.doFilter(request, response);


    }
}
