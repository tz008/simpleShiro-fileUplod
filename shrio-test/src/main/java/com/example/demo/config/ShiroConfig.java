package com.example.demo.config;


import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author sgl
 * @Date 2018-06-11 17:23
 */
@Configuration
public class ShiroConfig {


    /**
     * 凭证匹配器
     *
     * @return
     */


    /**
     * 自定义realm
     *
     * @return
     */
    @Bean
    public UserRealm userRealm() {
        UserRealm userRealm = new UserRealm();
        return userRealm;
    }

    /**
     * 安全管理器
     * 注：使用shiro-spring-boot-starter 1.4时，返回类型是SecurityManager会报错，直接引用shiro-spring则不报错
     *
     * @return
     */
    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm());
        return securityManager;
    }


    /**
     * 设置过滤规则
     *
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl("/testController/login");
        shiroFilterFactoryBean.setUnauthorizedUrl("/testController/no");

        //注意此处使用的是LinkedHashMap，是有顺序的，shiro会按从上到下的顺序匹配验证，匹配了就不再继续验证
        //所以上面的url要苛刻，宽松的url要放在下面，尤其是"/**"要放到最下面，如果放前面的话其后的验证规则就没作用了。
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
//        filterChainDefinitionMap.put("/testController/getAll","anon");
//        filterChainDefinitionMap.put("/testController/goLogin","anon");
//        filterChainDefinitionMap.put("/testController/login","anon");
//
////        注意：当授权拦截后，shiro会自动跳转到未授权页面
//        filterChainDefinitionMap.put("/testController/add","perms[user:add]");
        filterChainDefinitionMap.put("/testController/test","user");
        filterChainDefinitionMap.put("/testController/add","authc");
        filterChainDefinitionMap.put("/testController/update","authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

//    配置shirodialect  配合thymeleaf和 shiro标签使用
    @Bean
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }



}
