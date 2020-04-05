package com.qsz.bmss.config;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    UserAuthenticationProvider userAuthenticationProvider;
    @Autowired
    UserLoginSuccessHandler userLoginSuccessHandler;
    @Autowired
    UserLoginFailureHandler userLoginFailureHandler;
    @Autowired
    UserLogoutSuccessHandler userLogoutSuccessHandler;
    @Autowired
    UserAuthAccessDeniedHandler userAuthAccessDeniedHandler;
    @Autowired
    private UserAuthenticationEntryPointHandler userAuthenticationEntryPointHandler;

    @Bean
    LoginFilter loginFilter() throws Exception {
        LoginFilter loginFilter = new LoginFilter();
        loginFilter.setAuthenticationSuccessHandler(userLoginSuccessHandler);
        loginFilter.setAuthenticationFailureHandler(userLoginFailureHandler);
        loginFilter.setAuthenticationManager(authenticationManagerBean());
        loginFilter.setFilterProcessesUrl("/doLogin");
        return  loginFilter;
    }

    /**
     * 在这里配置自定义的验证服务
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(userAuthenticationProvider);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
//                设置不需要验证的资源和请求，从配置文件取
                .antMatchers(JWTConfig.antMatchers.split(","))
                .permitAll()
                .anyRequest().authenticated()
                .and()
                //设置未登录处理器
                .httpBasic().authenticationEntryPoint(userAuthenticationEntryPointHandler)
                .and()
                .logout()
                .logoutSuccessHandler(userLogoutSuccessHandler)
                .and()
                //设置没有权限处理类
                .exceptionHandling().accessDeniedHandler(userAuthAccessDeniedHandler)
                .and()
                //开启跨域
                .cors()
                .and()
                //取消跨域请求伪造防护
                .csrf().disable().exceptionHandling();
        //基于token不需要session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //禁用缓存
        http.headers().cacheControl();

        //使用json登录
        http.addFilterAt(loginFilter(), UsernamePasswordAuthenticationFilter.class);

        //设置token校验过滤器
        http.addFilter(new JWTAuthenticationTokenFilter(authenticationManager()));
    }


}
