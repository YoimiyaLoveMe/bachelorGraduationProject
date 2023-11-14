package com.example.shopping.config;

import com.example.shopping.entity.Customer;
import com.example.shopping.service.ICustomerService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wu
 */
public class CustomerRealm extends AuthorizingRealm {
    @Autowired
    private ICustomerService customerService;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //token(username,password)默认两个String形参
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String id = token.getUsername();
        Customer queryCustomer = new Customer();
        queryCustomer.setId(id);
        //根据(id)查询用户是否存在
        Customer customer = customerService.queryCustomerByCustomerId(queryCustomer);
        if(customer ==null){
            return null;
        }
        return new SimpleAuthenticationInfo(customer, customer.getPassword(),getName());
    }
}
