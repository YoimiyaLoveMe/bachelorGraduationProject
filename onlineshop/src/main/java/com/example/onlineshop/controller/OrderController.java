package com.example.onlineshop.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.onlineshop.common.ResultMapUtil;
import com.example.onlineshop.entity.*;
import com.example.onlineshop.service.IOrderCombinationService;
import com.example.onlineshop.service.IOrderRetailService;
import com.example.onlineshop.service.RedisService;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * 订单controller
 * @author wu
 * 2022.4.24
 */
@Controller
@RequestMapping(value = "/order")
public class OrderController {

    @RequestMapping
    public String order(){
        Set<String> set = redisService.getAllRole(((Worker) SecurityUtils.getSubject().getPrincipal()).getJob());
        if(set.contains("order")){
            return "/order";
        }
        else {
            return "/stop";
        }
    }

    @Resource
    private RedisService redisService;

    /**
     * 零售订单
     */

    @Resource
    private IOrderRetailService OrderRetailService;

    /**
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

    /**
     * 获取单件订单对象
     */
    @RequestMapping(value="/orderRetailGetById")
    @ResponseBody
    public Object getOrderRetail(Integer id){
        OrderRetail orderRetail = OrderRetailService.queryOrderRetailById(id);
        return orderRetail;
    }

    /**
     * 转向修改页面
     */
    @RequestMapping(value="/orderRetailQueryById")
    public String orderRetailInfo(@RequestParam(name="id")Integer id, Model model){
        OrderRetail orderRetail = OrderRetailService.queryOrderRetailById(id);
        model.addAttribute("obj",orderRetail);
        return "/orderComPage";
    }

    /**
     * 修改数据
     */
    @RequestMapping(value = "/orderRetailEdit")
    @ResponseBody
    public Object orderRetailEdit(OrderRetail orderRetail){
        try{
            int i = OrderRetailService.editOrderRetail(orderRetail);
            return ResultMapUtil.getHashMapSave(i);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 获取所有信息并返回列表格式
     */
    @RequestMapping(value = "/orderRetailList")
    @ResponseBody
    public Object orderRetailList(){
        List<OrderRetail> orderRetailList = OrderRetailService.queryOrderRetailList();
        return ResultMapUtil.getHashMapList(orderRetailList);
    }

    /**
     * 逻辑删除数据
     */
    @RequestMapping(value = "/orderRetailDelById")
    @ResponseBody
    public Object orderRetailDelById(Integer id){
        try{
            int i = OrderRetailService.lodelOrderRetailById(id);
            return ResultMapUtil.getHashMapSave(i);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 整机订单
     */
    
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

    /**
     * 获取整机订单对象
     */
    @RequestMapping(value="/orderCombinationGetById")
    @ResponseBody
    public Object getOrderCombination(Integer id){
        OrderCombination orderCombination = OrderCombinationService.queryOrderCombinationById(id);
        return orderCombination;
    }

    /**
     * 转向修改页面
     */
    @RequestMapping(value="/orderCombinationQueryById")
    public String orderCombinationInfo(@RequestParam(name="id")Integer id, Model model){
        OrderCombination orderCombination = OrderCombinationService.queryOrderCombinationById(id);
        model.addAttribute("obj",orderCombination);
        return "/orderComPage";
    }

    /**
     * 修改数据
     */
    @RequestMapping(value = "/orderCombinationEdit")
    @ResponseBody
    public Object orderCombinationEdit(OrderCombination orderCombination){
        try{
            int i = OrderCombinationService.editOrderCombination(orderCombination);
            return ResultMapUtil.getHashMapSave(i);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 获取所有信息并返回列表格式
     */
    @RequestMapping(value = "/orderCombinationList")
    @ResponseBody
    public Object orderCombinationList(){
        List<OrderCombination> orderCombinationList = OrderCombinationService.queryOrderCombinationList();
        return ResultMapUtil.getHashMapList(orderCombinationList);
    }

    /**
     * 逻辑删除数据
     */
    @RequestMapping(value = "/orderCombinationDelById")
    @ResponseBody
    public Object orderCombinationDelById(Integer id){
        try{
            int i = OrderCombinationService.lodelOrderCombinationById(id);
            return ResultMapUtil.getHashMapSave(i);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

}
