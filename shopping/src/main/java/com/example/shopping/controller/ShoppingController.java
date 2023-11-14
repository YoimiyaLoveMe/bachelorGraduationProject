package com.example.shopping.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.shopping.common.ResultMapUtil;
import com.example.shopping.entity.*;
import com.example.shopping.service.IOrderCombinationService;
import com.example.shopping.service.IOrderRetailService;
import com.example.shopping.service.RedisService;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 商城功能的controller
 * @author wu
 * 2022.4.24
 */
@Controller
@RequestMapping(value = "/shop")
public class ShoppingController {

    @Resource
    private RedisService redisService;

    /**
     * 获取用户收货地址
     */
    @RequestMapping(value = "/getAddress")
    @ResponseBody
    public List<String> getAddress(){
        Customer customer = (Customer) SecurityUtils.getSubject().getPrincipal();
        List<String> list = redisService.getAddress(customer.getId());
        return list;
    }

    @RequestMapping(value = "/addressPage")
    public String addressPager(){
        return "/addressPage";
    }

    /**
     * 新增地址
     * @param address
     * @param flag
     * flag: 是否设置为默认地址
     */
    @RequestMapping(value = "/addAddress")
    @ResponseBody
    public Object addAdress(String address,String flag){
        try{
            Customer customer = (Customer) SecurityUtils.getSubject().getPrincipal();
            redisService.addAddress(customer.getId(),address,flag);
            return ResultMapUtil.getHashMapSave(1);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 删除地址
     */
    @RequestMapping(value = "/delAddress")
    @ResponseBody
    public Object delAddress(String address){
        try{
            Customer customer = (Customer) SecurityUtils.getSubject().getPrincipal();
            redisService.delAddress(customer.getId(),address);
            return ResultMapUtil.getHashMapSave(1);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 设置默认地址
     */
    @RequestMapping(value = "/setDefaultAddress")
    @ResponseBody
    public Object setDefaultAddress(String address){
        try{
            Customer customer = (Customer) SecurityUtils.getSubject().getPrincipal();
            redisService.setDefaultAddress(customer.getId(),address);
            return ResultMapUtil.getHashMapSave(1);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 获取心愿单和购物车商品数量
     */
    @RequestMapping(value = "/getLoveAndCart")
    @ResponseBody
    public List getLoveAndCart(String id){
        List list = new ArrayList();
        list.add(redisService.getLoveNumber(id));
        list.add(redisService.getCartNumber(id));
        return list;
    }

    /**
     * 获取心愿单详情
     */
    @RequestMapping(value = "getLove")
    @ResponseBody
    public Set<String> getLove(){
        Customer customer = (Customer) SecurityUtils.getSubject().getPrincipal();
        Set<String> set = redisService.getLove(customer.getId());
        return set;
    }

    /**
     * 收藏商品,添加至心愿单
     */
    @RequestMapping(value = "/addLove")
    @ResponseBody
    public Object addLove(Integer id){
        try{
            Customer customer = (Customer) SecurityUtils.getSubject().getPrincipal();
            redisService.addLove(customer.getId(),id);
            return ResultMapUtil.getHashMapSave(1);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 取消收藏，商品移出心愿单
     */
    @RequestMapping(value = "/delLove")
    @ResponseBody
    public Object delLove(Integer id){
        try{
            Customer customer = (Customer) SecurityUtils.getSubject().getPrincipal();
            redisService.delLove(customer.getId(),id);
            return ResultMapUtil.getHashMapSave(1);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 获取购物车详情
     */
    @RequestMapping(value = "getCart")
    @ResponseBody
    public Map<String,String> getCart(){
        Customer customer = (Customer) SecurityUtils.getSubject().getPrincipal();
        Map<String,String> map = redisService.getCart(customer.getId());
        return map;
    }

    /**
     * 添加至购物车
     */
    @RequestMapping(value = "/addCart")
    @ResponseBody
    public Object addCart(Integer id,Integer number){
        try{
            Customer customer = (Customer) SecurityUtils.getSubject().getPrincipal();
            redisService.addCart(customer.getId(),id,number);
            return ResultMapUtil.getHashMapSave(1);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 商品移出购物车
     */
    @RequestMapping(value = "/delCart")
    @ResponseBody
    public Object delCart(Integer id){
        try{
            Customer customer = (Customer) SecurityUtils.getSubject().getPrincipal();
            redisService.delCart(customer.getId(),id);
            return ResultMapUtil.getHashMapSave(1);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 保存整机配置单
     */
    @RequestMapping(value = "/saveCombination")
    @ResponseBody
    public Object saveCombination(String combination){
        try{
            Customer customer = (Customer) SecurityUtils.getSubject().getPrincipal();
            redisService.setCombination(customer.getId(),combination);
            return ResultMapUtil.getHashMapSave(1);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 获取整机配置单详情
     */
    @RequestMapping(value = "/getCombination")
    @ResponseBody
    public Set<String> getCombination(){
        Customer customer = (Customer) SecurityUtils.getSubject().getPrincipal();
        Set<String> set = redisService.getCombination(customer.getId());
        return set;
    }

    @Resource
    private IOrderRetailService OrderRetailService;

    /**
     * 查询零售订单
     * 分页查询列表
     * 非返回页面
     */
    @RequestMapping(value = "/orderRetailQueryPage")
    @ResponseBody
    public Object orderRetailQueryPage(@RequestParam(defaultValue = "1")int page, @RequestParam(defaultValue = "10")int limit, String param){
        try {
            IPage<OrderRetail> iPage = OrderRetailService.selectOrderRetailPage(page,limit,param);
            return ResultMapUtil.getHashMapMysqlPage(iPage);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    @Resource
    private IOrderCombinationService OrderCombinationService;

    @RequestMapping(value ="/orderCombinationQueryPage")
    @ResponseBody
    public Object orderCombinationQueryPage(@RequestParam(defaultValue = "1")int page, @RequestParam(defaultValue = "10")int limit, String param){
        try {
            IPage<OrderCombination> iPage = OrderCombinationService.selectOrderCombinationPage(page,limit,param);
            return ResultMapUtil.getHashMapMysqlPage(iPage);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

}
