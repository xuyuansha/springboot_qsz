package com.qsz.bmss.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //开启权限注解,默认是关闭的
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

    @Bean
    public DefaultWebSecurityExpressionHandler userSecurityExpressionHandler(){
        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        handler.setPermissionEvaluator(new UserPermissionEvaluator());
        return handler;
    }

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
        auth.authenticationProvider(userAuthenticationProvider);
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
//                .antMatchers("/system/**").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/**").access("hasRole('ROLE_USER')")
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

        //使用json登录
        http.addFilterAt(loginFilter(), UsernamePasswordAuthenticationFilter.class);


    }


}
