package com.example.shopping.controller;

import com.example.shopping.common.ResultMapUtil;
import com.example.shopping.entity.Customer;
import com.example.shopping.service.ICustomerService;
import com.example.shopping.service.RedisService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 用户相关的controller
 * @author wu
 * 2022.3
 */
@Controller
public class CustomerController {
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
     * 登录
     */
    @RequestMapping(value = "/index")
    public String index(){
        return "/index";
    }

    /**
     * 获取当前用户
     */
    @RequestMapping(value = "/getUser")
    @ResponseBody
    public Customer getUser(){
        Customer customer = (Customer) SecurityUtils.getSubject().getPrincipal();
        return customer;
    }



    /**
     * 退出系统
     */
    @RequestMapping(value = "/logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/index";
    }

    @Resource
    private ICustomerService customerService;


    /**
     * 向数据库添加数据
     */
    @RequestMapping(value = "/customerAdd")
    @ResponseBody
    public Object customerAdd(Customer customer){
        try{
            customer.setPassword(DigestUtils.md5DigestAsHex(customer.getPassword().getBytes()));
            customerService.addCustomer(customer);
            return ResultMapUtil.getHashMapLogin("注册成功","1");
        }catch (Exception e){
            return ResultMapUtil.getHashMapLogin("用户已存在","4");
        }
    }

    /**
     * 转向安全信息页面
     */
    @RequestMapping(value="/customerSafe")
    public String customerSafe(Model model){
        Customer customer = (Customer) SecurityUtils.getSubject().getPrincipal();
        model.addAttribute("obj", customer);
        return "/customerSafePage";
    }

    /**
     * 修改数据
     */
    @RequestMapping(value = "/customerEdit")
    @ResponseBody
    public Object customerEdit(Customer customer){
        try{
            //加密
            customer.setPassword(DigestUtils.md5DigestAsHex(customer.getPassword().getBytes()));
            int i = customerService.editCustomer(customer);
            return ResultMapUtil.getHashMapSave(i);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }


}
