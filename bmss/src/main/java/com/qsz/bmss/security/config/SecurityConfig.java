package com.qsz.bmss.security.config;

import com.qsz.bmss.security.filter.CustomFilterInvocationSecurityMetadataSource;
import com.qsz.bmss.security.filter.CustomUrlDecisionMananger;
import com.qsz.bmss.security.filter.JWTAuthenticationTokenFilter;
import com.qsz.bmss.security.filter.LoginFilter;
import com.qsz.bmss.security.handler.*;
import com.qsz.bmss.security.service.SelfUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //开启权限注解,默认是关闭的
public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    @Autowired
//    UserAuthenticationProvider userAuthenticationProvider;
    @Autowired
     SelfUserService selfUserService;
    @Autowired
    UserLoginSuccessHandler userLoginSuccessHandler;
    @Autowired
    UserLoginFailureHandler userLoginFailureHandler;
    @Autowired
    UserLogoutSuccessHandler userLogoutSuccessHandler;
    @Autowired
    UserAuthAccessDeniedHandler userAuthAccessDeniedHandler;
    @Autowired
    UserAuthenticationEntryPointHandler userAuthenticationEntryPointHandler;
    @Autowired
    private CustomUrlDecisionMananger customUrlDecisionMananger;
    @Autowired
    private CustomFilterInvocationSecurityMetadataSource customFilterInvocationSecurityMetadataSource;
    /**
     * 加密方式
     * @return
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

   /* @Bean
    public DefaultWebSecurityExpressionHandler userSecurityExpressionHandler(){
        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        handler.setPermissionEvaluator(new UserPermissionEvaluator());
        return handler;
    }*/

    /*连接数据库后没有用了
    @Bean
    RoleHierarchy roleHierarchy(){
       RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
       roleHierarchy.setHierarchy("ROLE_ADMIN>ROLE_USER");
       log.info("Role.....");
       return roleHierarchy;
   }*/
    /**
     * 登录过滤器
     * @return
     * @throws Exception
     */
    @Bean
    LoginFilter loginFilter() throws Exception {
        LoginFilter loginFilter = new LoginFilter();
        loginFilter.setAuthenticationSuccessHandler(userLoginSuccessHandler);
        loginFilter.setAuthenticationFailureHandler(userLoginFailureHandler);
        loginFilter.setAuthenticationManager(authenticationManagerBean());

        loginFilter.setFilterProcessesUrl("/user/login");
        return  loginFilter;
    }

    /**
     * 在这里配置自定义的验证服务
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(userAuthenticationProvider);
        auth.userDetailsService(selfUserService);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                        object.setAccessDecisionManager(customUrlDecisionMananger);
                        object.setSecurityMetadataSource(customFilterInvocationSecurityMetadataSource);
                        return object;
                    }
                })
//                设置不需要验证的资源和请求，从配置文件取
                .antMatchers(JWTConfig.antMatchers.split(",")).permitAll()
//                .antMatchers("/system/**").hasRole("ADMIN")
                .antMatchers("/").permitAll()
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
        //设置token校验过滤器
        http.addFilter(new JWTAuthenticationTokenFilter(authenticationManager()));
        //禁用缓存
        http.headers().cacheControl();
        //使用json登录替换原来的key-value登录
        http.addFilterAt(loginFilter(), UsernamePasswordAuthenticationFilter.class);
    }


}
