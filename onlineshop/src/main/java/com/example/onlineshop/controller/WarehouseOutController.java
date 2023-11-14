package com.example.onlineshop.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.onlineshop.common.ResultMapUtil;
import com.example.onlineshop.entity.WarehouseOut;
import com.example.onlineshop.entity.Worker;
import com.example.onlineshop.service.IPurchaseService;
import com.example.onlineshop.service.IWarehouseOutService;
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
 * 入库管理controller
 * @author wu
 * 2022.4.23
 */
@Controller
@RequestMapping(value = "/warehouseOut")

public class WarehouseOutController {

    @Resource
    private IWarehouseOutService warehouseOutService;

    @RequestMapping
    public String warehouseOut(){
        Set<String> set = redisService.getAllRole(((Worker) SecurityUtils.getSubject().getPrincipal()).getJob());
        if(set.contains("warehouseOut")){
            return "/warehouseOut";
        }
        else {
            return "/stop";
        }
    }

    /**
     * 转向新增页面
     */
    @RequestMapping(value="/warehouseOutPage")
    public String toWarehouseOutAdd(){
        return "/warehouseOutPage";
    }

    @Resource
    private RedisService redisService;

    @Resource
    private IPurchaseService purchaseService;

    /**
     * 向数据库添加数据
     */
    @RequestMapping(value = "/warehouseOutAdd")
    @ResponseBody
    public Object warehouseOutAdd(WarehouseOut warehouseOut){
        try{
            //自动设置id
            warehouseOut.setId(redisService.getWarehouseOutId());

            //自动设置添加者
            Worker worker = (Worker) SecurityUtils.getSubject().getPrincipal();
            warehouseOut.setWorkerId(worker.getId());

            int i = warehouseOutService.addWarehouseOut(warehouseOut);

            //修改库位库存
            redisService.setWarehouseStock(warehouseOut.getWarehouseId(),-(warehouseOut.getNumber()),warehouseOut.getGoodsId());
            //修改商品库存，已在下单页面处理
            //redisService.setGoodsStock(warehouseOut.getGoodsId(),-warehouseOut.getNumber());
            //出库单添加进待发货集合
            redisService.setOutToOut(warehouseOut.getId());

            return ResultMapUtil.getHashMapSave(i);
        }catch (Exception e){
            System.out.println(e);
            return ResultMapUtil.getHashMapLogin("错误","2");
//            return ResultMapUtil.getHashMapException(e);
        }
    }


    /**
     * 删除数据
     */
    @RequestMapping(value = "/warehouseOutDelById")
    @ResponseBody
    public Object warehouseOutDelById(Integer id){

        try{
            int i = warehouseOutService.lodelWarehouseOutById(id);
            return ResultMapUtil.getHashMapSave(i);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 获取对象
     */
    @RequestMapping(value="/warehouseOutGetById")
    @ResponseBody
    public Object getWarehouseOut(Integer id){
        WarehouseOut warehouseOut = warehouseOutService.queryWarehouseOutById(id);
        return warehouseOut;
    }

    /**
     * 转向修改页面
     */
    @RequestMapping(value="/warehouseOutQueryById")
    public String warehouseOutInfo(@RequestParam(name="id")Integer id,Model model){
        WarehouseOut warehouseOut = warehouseOutService.queryWarehouseOutById(id);
        model.addAttribute("obj",warehouseOut);
        return "/warehouseOutPage";
    }

    /**
     * 修改数据
     */
    @RequestMapping(value = "/warehouseOutEdit")
    @ResponseBody
    public Object warehouseOutEdit(WarehouseOut warehouseOut){
        try{
            //自动更新编辑者
            Worker worker = (Worker) SecurityUtils.getSubject().getPrincipal();
            warehouseOut.setWorkerId(worker.getId());

            int i = warehouseOutService.editWarehouseOut(warehouseOut);
            return ResultMapUtil.getHashMapSave(i);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 分页查询列表
     * 非返回页面
     */
    @RequestMapping(value = "/warehouseOutQueryPage")
    @ResponseBody
    public Object warehouseOutQueryPage(@RequestParam(defaultValue = "1")int page, @RequestParam(defaultValue = "10")int limit, String param){
        try {
            IPage<WarehouseOut> iPage = warehouseOutService.selectWarehouseOutPage(page,limit,param);
            return ResultMapUtil.getHashMapMysqlPage(iPage);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 获取所有信息并返回列表格式
     */
    @RequestMapping(value = "/warehouseOutList")
    @ResponseBody
    public Object warehouseOutList(){
        List<WarehouseOut> warehouseOutList = warehouseOutService.queryWarehouseOutList();
        return ResultMapUtil.getHashMapList(warehouseOutList);
    }
}

