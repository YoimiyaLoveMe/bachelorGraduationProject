package com.example.onlineshop.config;

import com.example.onlineshop.entity.Worker;
import com.example.onlineshop.service.IWorkerService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wu
 */
public class WorkerRealm extends AuthorizingRealm {
    @Autowired
    private IWorkerService workerService;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //token(username,password)默认两个String形参
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String id = token.getUsername();
        Worker queryWorker = new Worker();
        queryWorker.setId(id);
        //根据(id)查询用户是否存在
        Worker worker = workerService.queryWorkerByWorkerId(queryWorker);
        if(worker==null){
            return null;
        }
        return new SimpleAuthenticationInfo(worker,worker.getPassword(),getName());
    }
}
