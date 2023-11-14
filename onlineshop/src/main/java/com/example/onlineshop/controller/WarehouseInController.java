package com.example.onlineshop.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.onlineshop.common.ResultMapUtil;
import com.example.onlineshop.entity.WarehouseIn;
import com.example.onlineshop.entity.Worker;
import com.example.onlineshop.service.IWarehouseInService;
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
 * 入库管理controller
 * @author wu
 * 2022.4.23
 */
@Controller
@RequestMapping(value = "/warehouseIn")

public class WarehouseInController {

    @Resource
    private IWarehouseInService warehouseInService;

    @RequestMapping
    public String warehouseIn(){
        Set<String> set = redisService.getAllRole(((Worker) SecurityUtils.getSubject().getPrincipal()).getJob());
        if(set.contains("warehouseIn")){
            return "/warehouseIn";
        }
        else {
            return "/stop";
        }
    }

    /**
     * 转向新增页面
     */
    @RequestMapping(value="/warehouseInPage")
    public String toWarehouseInAdd(){
        return "/warehouseInPage";
    }

    @Resource
    private RedisService redisService;

    @Resource
    private IPurchaseService purchaseService;

    /**
     * 向数据库添加数据
     */
    @RequestMapping(value = "/warehouseInAdd")
    @ResponseBody
    public Object warehouseInAdd(WarehouseIn warehouseIn){
        try{
            //自动设置id
            warehouseIn.setId(redisService.getWarehouseInId());

            //自动设置添加者
            Worker worker = (Worker) SecurityUtils.getSubject().getPrincipal();
            warehouseIn.setWorkerId(worker.getId());

            int i = warehouseInService.addWarehouseIn(warehouseIn);

            /**
             * 对比字符串应用*.equals
             * 使用 == 可能无法判断
             */

            //统计数量,与收货单对照
            if("物流收货".equals(warehouseIn.getSourceCategory())){
                redisService.countLogisticsNumber(warehouseIn.getSourceId(),warehouseIn.getNumber());
            }

            //记录收货数量,修改库存
            //修改库位库存
            redisService.setWarehouseStock(warehouseIn.getWarehouseId(),warehouseIn.getNumber(),warehouseIn.getGoodsId());
            //修改商品库存
            redisService.setGoodsStock(warehouseIn.getGoodsId(),warehouseIn.getNumber());

            return ResultMapUtil.getHashMapSave(i);
        }catch (Exception e){
            return ResultMapUtil.getHashMapLogin("错误","2");
//            return ResultMapUtil.getHashMapException(e);
        }
    }


    /**
     * 删除数据
     */
    @RequestMapping(value = "/warehouseInDelById")
    @ResponseBody
    public Object warehouseInDelById(Integer id){

        try{
            int i = warehouseInService.lodelWarehouseInById(id);
            return ResultMapUtil.getHashMapSave(i);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 获取对象
     */
    @RequestMapping(value="/warehouseInGetById")
    @ResponseBody
    public Object getWarehouseIn(Integer id){
        WarehouseIn warehouseIn = warehouseInService.queryWarehouseInById(id);
        return warehouseIn;
    }

    /**
     * 转向修改页面
     */
    @RequestMapping(value="/warehouseInQueryById")
    public String warehouseInInfo(@RequestParam(name="id")Integer id,Model model){
        WarehouseIn warehouseIn = warehouseInService.queryWarehouseInById(id);
        model.addAttribute("obj",warehouseIn);
        return "/warehouseInPage";
    }

    /**
     * 修改数据
     */
    @RequestMapping(value = "/warehouseInEdit")
    @ResponseBody
    public Object warehouseInEdit(WarehouseIn warehouseIn){
        try{
            //自动更新编辑者
            Worker worker = (Worker) SecurityUtils.getSubject().getPrincipal();
            warehouseIn.setWorkerId(worker.getId());

            int i = warehouseInService.editWarehouseIn(warehouseIn);
            return ResultMapUtil.getHashMapSave(i);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 分页查询列表
     * 非返回页面
     */
    @RequestMapping(value = "/warehouseInQueryPage")
    @ResponseBody
    public Object warehouseInQueryPage(@RequestParam(defaultValue = "1")int page, @RequestParam(defaultValue = "10")int limit, String param){
        try {
            IPage<WarehouseIn> iPage = warehouseInService.selectWarehouseInPage(page,limit,param);
            return ResultMapUtil.getHashMapMysqlPage(iPage);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 获取所有信息并返回列表格式
     */
    @RequestMapping(value = "/warehouseInList")
    @ResponseBody
    public Object warehouseInList(){
        List<WarehouseIn> warehouseInList = warehouseInService.queryWarehouseInList();
        return ResultMapUtil.getHashMapList(warehouseInList);
    }
}

