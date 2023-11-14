package com.example.shopping.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author wu
 */
@Configuration
public class ShiroFilterConfiguration {
    /**
     * 创建过滤工厂Bean
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager());
        /*Shiro内置过滤器，实现权限相关的拦截器,前置代码优先*/
        Map<String,String> filterMap = new LinkedHashMap<>();
        /*anon:无需登录即可访问,接口、页面、资源*/
        filterMap.put("/test","anon");
        filterMap.put("/static/**","anon");
        filterMap.put("/favicon.ico", "anon");
        filterMap.put("/login","anon");
        filterMap.put("/customerAdd","anon");
        filterMap.put("/toLogin","anon");
        filterMap.put("/register","anon");
        filterMap.put("/getUser","anon");
        filterMap.put("/goods/*","anon");
        /*authc:登录访问*/
        filterMap.put("/**","authc");
        filterMap.put("/clock","authc");
        filterMap.put("/customersafe","authc");
        filterMap.put("/customerEdit","authc");
        filterMap.put("/getAddress","authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        /*不登录自动转向的页面*/
        shiroFilterFactoryBean.setLoginUrl("/index");
        /*登录后自动转向的页面*/
        //shiroFilterFactoryBean.setSuccessUrl("/index");
        return shiroFilterFactoryBean;
    }

    /**
     * 创建Realm认证
     */
    @Bean
    public CustomerRealm customerRealm(){
        return new CustomerRealm();
    }
    /**
     * thymeleaf整合Shiro
     */
    @Bean
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }

    @Bean
    public DefaultWebSecurityManager securityManager(){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(customerRealm());
        return securityManager;
    }

}
