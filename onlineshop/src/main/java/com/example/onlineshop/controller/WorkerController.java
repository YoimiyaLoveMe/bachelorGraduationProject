package com.example.onlineshop.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.onlineshop.common.ResultMapUtil;
import com.example.onlineshop.entity.Worker;
import com.example.onlineshop.entity.Worker;
import com.example.onlineshop.service.IWorkerService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户相关的controller
 * @author wu
 * 2022.3
 */
@Controller
public class WorkerController {
    /**
     * 转向登录页面
     */
    @RequestMapping(value = "/login")
    public String login(){
        return "/login";
    }
    /**
     * 判断用户登录是否成功
     */
    @RequestMapping(value = "/toLogin")
    @ResponseBody
    public Object toLogin(String id,String password){
        char[] chars = id.toCharArray();
        for (int i=0; i < chars.length; i++) {
            if(chars[i]<48||chars[i]>57){
                return ResultMapUtil.getHashMapLogin("非法用户名","0");
            }
        }
        //进行加密
        String encryption = DigestUtils.md5DigestAsHex(password.getBytes());
        //创建subject实例
        Subject subject = SecurityUtils.getSubject();
        //将用户名与密码封装至token（一种加密传输）
        UsernamePasswordToken token = new UsernamePasswordToken(id,encryption);
        //token默认要求传入username和password两个参数，但实际中不一定只能是用户名和密码，只是一种形参，可以代指其它
        try {
            //通过token登录
            subject.login(token);
        }catch (UnknownAccountException e){
            return ResultMapUtil.getHashMapLogin("用户不存在","3");
        }catch (IncorrectCredentialsException e){
            return ResultMapUtil.getHashMapLogin("密码错误","2");
        }
        return ResultMapUtil.getHashMapLogin("验证成功","1");
    }
    
    /**
     * 登录后获取当前用户
     */
    @RequestMapping(value = "/index")
    public String index(Model model){
        Worker worker = (Worker) SecurityUtils.getSubject().getPrincipal();
        model.addAttribute("obj",worker);
        return "/index";
    }
    
    /**
     * 退出系统
     */
    @RequestMapping(value = "/logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/login";
    }

    @Resource
    private IWorkerService workerService;


    /**
     * 转向安全信息页面
     */
    @RequestMapping(value="/workerSafe")
    public String workerSafe(Model model){
        Worker worker = (Worker) SecurityUtils.getSubject().getPrincipal();
        model.addAttribute("obj",worker);
        return "/workerSafePage";
    }

    /**
     * 修改安全信息
     */
    @RequestMapping(value = "/workerEdit")
    @ResponseBody
    public Object workerEdit(Worker worker){
        try{
            //加密
            worker.setPassword(DigestUtils.md5DigestAsHex(worker.getPassword().getBytes()));
            int i = workerService.editWorker(worker);
            return ResultMapUtil.getHashMapSave(i);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 错误日志
     * 2022.4.1
     * 打卡功能出现异常，成功操作数据库，但前端未接收到返回数据且控制台报错
     * 报错信息：Error resolving template [clock], template might not exist or might not be accessible by any of the configured Template Resolvers
     * 解决：添加@ResponseBody
     * 原因：@Controller 注解时，spring默认方法返回的是view对象（页面）。而加上@ResponseBody，则方法返回的就是具体对象，可以是json数据，也可以是字符形式。
     */

    /**
     * 上下班打卡
     */
    @RequestMapping(value = "/clock")
    @ResponseBody
    public Object clock(){
        Worker worker = (Worker) SecurityUtils.getSubject().getPrincipal();
        int working = worker.getWorking();
        int i = 0;
        if(working == 0){
            i = workerService.clockin(worker.getId());
        }else if(working == 1){
            i = workerService.clockout(worker.getId());
        }
        return ResultMapUtil.getHashMapSave(i);
    }

}
