package com.qsz.bmss.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
                if (!StringUtils.isEmpty(userName) && !StringUtils.isEmpty(userId)){
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userName,userId);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }catch (ExpiredJwtException e){
                log.error("token过期",e.getMessage());
            }catch (Exception e){
                log.error("token无效",e.getMessage());
            }
        }
        chain.doFilter(request,response);

    }
}
