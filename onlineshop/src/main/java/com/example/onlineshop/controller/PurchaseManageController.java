package com.example.onlineshop.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.onlineshop.common.ResultMapUtil;
import com.example.onlineshop.entity.Purchase;
import com.example.onlineshop.entity.Worker;
import com.example.onlineshop.service.IPurchaseService;
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
 * 采购管理controller
 * @author wu
 * 2022.4.22
 */
@Controller
@RequestMapping(value = "/purchaseManage")

public class PurchaseManageController {
    
    @Resource
    private IPurchaseService purchaseService;

    @RequestMapping
    public String purchase(){
        Set<String> set = redisService.getAllRole(((Worker) SecurityUtils.getSubject().getPrincipal()).getJob());
        if(set.contains("purchaseManage")){
            return "/purchaseManage";
        }
        else {
            return "/stop";
        }

    }

    /**
     * 转向新增页面
     */
    @RequestMapping(value="/purchasePage")
    public String toPurchaseAdd(){
        return "/purchasePage";
    }

    @Resource
    private RedisService redisService;

    /**
     * 向数据库添加数据
     */
    @RequestMapping(value = "/purchaseAdd")
    @ResponseBody
    public Object jobAdd(Purchase purchase){
        try{
            //自动设置id
            purchase.setId(redisService.getPurchaseId());
            //自动设置添加者
            Worker worker = (Worker) SecurityUtils.getSubject().getPrincipal();
            purchase.setPurchaseId(worker.getId());
            //默认状态
            purchase.setState("待审核");

            int i = purchaseService.addPurchase(purchase);
            ResultMapUtil.getHashMapLogin("添加成功","1");
            return ResultMapUtil.getHashMapSave(i);
        }catch (Exception e){
            return ResultMapUtil.getHashMapLogin("错误","2");
            //return ResultMapUtil.getHashMapException(e);
        }
    }


    /**
     * 采购单审核打回
     */
    @RequestMapping(value = "/purchaseRefuseById")
    @ResponseBody
    public Object purchaseRefuseById(Integer id){
        try{
            Worker worker = (Worker) SecurityUtils.getSubject().getPrincipal();
            Purchase purchase = purchaseService.queryPurchaseById(id);
            purchase.setExamineId(worker.getId());
            purchaseService.editPurchase(purchase);

            purchaseService.refuse(id);
            return ResultMapUtil.getHashMapSave(1);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 采购单审核通过
     */
    @RequestMapping(value = "/purchaseExamineById")
    @ResponseBody
    public Object purchaseExamineById(Integer id){
        try{
            Worker worker = (Worker) SecurityUtils.getSubject().getPrincipal();
            Purchase purchase = purchaseService.queryPurchaseById(id);
            purchase.setExamineId(worker.getId());
            purchaseService.editPurchase(purchase);

            purchaseService.examine(id);
            //记录采购数量用于收货统计
            redisService.setPurchaseNumber(id,purchaseService.queryPurchaseById(id).getNumber());
            return ResultMapUtil.getHashMapSave(1);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 查询采购单商品的收货数量
     */
    @RequestMapping(value = "/getNumber")
    @ResponseBody
    public Integer getPurchaseNumber(Integer id){
        return redisService.getPurchaseNumber(id);
    }

    /**
     * 获取供货对象
     */
    @RequestMapping(value="/purchaseGetById")
    @ResponseBody
    public Object getPurchase(Integer id){
        Purchase purchase = purchaseService.queryPurchaseById(id);
        return purchase;
    }

    /**
     * 分页查询列表
     * 非返回页面
     */
    @RequestMapping(value = "/purchaseQueryPage")
    @ResponseBody
    public Object purchaseQueryPage(@RequestParam(defaultValue = "1")int page, @RequestParam(defaultValue = "10")int limit, String param){
        try {
            IPage<Purchase> iPage = purchaseService.selectPurchasePage(page,limit,param);
            return ResultMapUtil.getHashMapMysqlPage(iPage);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 获取所有信息并返回列表格式
     */
    @RequestMapping(value = "/purchaseList")
    @ResponseBody
    public Object purchaseList(){
        List<Purchase> purchaseList = purchaseService.queryPurchaseList();
        return ResultMapUtil.getHashMapList(purchaseList);
    }

}

