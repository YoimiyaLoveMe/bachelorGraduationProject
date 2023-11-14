package com.example.onlineshop.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.onlineshop.common.ResultMapUtil;
import com.example.onlineshop.entity.StockCom;
import com.example.onlineshop.entity.Warehouse;
import com.example.onlineshop.entity.Worker;
import com.example.onlineshop.service.*;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Tuple;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * 供货管理controller
 * @author wu
 * 2022.4.20
 */
@Controller
@RequestMapping(value = "/warehouse")

public class WarehouseController {

    /**
     * 仓储管理相关的controller
     */
    @Resource
    private IWarehouseService warehouseService;

    @RequestMapping
    public String job(){
        Set<String> set = redisService.getAllRole(((Worker) SecurityUtils.getSubject().getPrincipal()).getJob());
        if(set.contains("warehouse")){
            return "/warehouse";
        }
        else {
            return "/stop";
        }
    }

    /**
     * 转向新增页面
     */
    @RequestMapping(value="/warehousePage")
    public String toWarehouseAdd(){
        return "/warehousePage";
    }

    @Resource
    private RedisService redisService;

    /**
     * 向数据库添加数据
     */
    @RequestMapping(value = "/warehouseAdd")
    @ResponseBody
    public Object jobAdd(Warehouse warehouse){
        try{
            //自动设置id
            warehouse.setId(redisService.getWarehouseId());

            int i = warehouseService.addWarehouse(warehouse);
            ResultMapUtil.getHashMapLogin("添加成功","1");
            return ResultMapUtil.getHashMapSave(i);
        }catch (Exception e){
            return ResultMapUtil.getHashMapLogin("错误","2");
            //return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 删除数据
     */
    @RequestMapping(value = "/warehouseDelById")
    @ResponseBody
    public Object warehouseDelById(Integer warehouse_id){

        try{
            int i = warehouseService.delWarehouseById(warehouse_id);
            return ResultMapUtil.getHashMapSave(i);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 获取供货对象
     */
    @RequestMapping(value="/warehouseGetById")
    @ResponseBody
    public Object getWarehouse(Integer id){
        Warehouse warehouse = warehouseService.queryWarehouseById(id);
        return warehouse;
    }

    /**
     * 转向修改页面
     */
    @RequestMapping(value="/warehouseQueryById")
    public String warehouseInfo(@RequestParam(name="id")Integer id,Model model){
        Warehouse warehouse = warehouseService.queryWarehouseById(id);
        model.addAttribute("obj",warehouse);
        return "/warehousePage";
    }

    /**
     * 修改数据
     */
    @RequestMapping(value = "/warehouseEdit")
    @ResponseBody
    public Object warehouseEdit(Warehouse warehouse){
        try{

            int i = warehouseService.editWarehouse(warehouse);
            return ResultMapUtil.getHashMapSave(i);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 分页查询列表
     * 非返回页面
     */
    @RequestMapping(value = "/warehouseQueryPage")
    @ResponseBody
    public Object warehouseQueryPage(@RequestParam(defaultValue = "1")int page, @RequestParam(defaultValue = "10")int limit, String param){
        try {
            IPage<Warehouse> iPage = warehouseService.selectWarehousePage(page,limit,param);
            return ResultMapUtil.getHashMapMysqlPage(iPage);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 获取所有信息并返回列表格式
     */
    @RequestMapping(value = "/warehouseList")
    @ResponseBody
    public Object warehouseList(){
        List<Warehouse> warehouseList = warehouseService.queryWarehouseList();
        return ResultMapUtil.getHashMapList(warehouseList);
    }

    /**
     * 获取库位总库存
     */
    @RequestMapping(value = "/stock")
    @ResponseBody
    public int getStockById(Integer id){
        int stock = 0;
        Set<Tuple> tuples = redisService.getWarehouseStock(id);
        for(Tuple tuple :tuples){
            stock += (int) tuple.getScore();
        }
        return stock;
    }

    /**
     * 转向库位库存页面
     */
    @RequestMapping(value = "/warehouseStock")
    public String getWarehouseStockById(Integer id,Model model){
        model.addAttribute("id",id);
        return "/warehouseStock";
    }

    /**
     * 获取库位库存
     */
    @RequestMapping(value = "/getStockInfo")
    @ResponseBody
    public List getGoodsStock(Integer id){
        Set<Tuple> tuples = redisService.getWarehouseStock(id);
        List list = new ArrayList();
        for (Tuple tuple : tuples){
            StockCom stockCom = new StockCom();
            stockCom.setId(Integer.parseInt(tuple.getElement()));
            stockCom.setStock((int)tuple.getScore());
            stockCom.setCategory(redisService.getGoodsCategory(Integer.parseInt(tuple.getElement())));
            stockCom.setName(productName(Integer.parseInt(tuple.getElement())));
            list.add(stockCom);
        }
        return list;
    }

    /**
     * 依据商品id查询有库存的库位
     * @param id 库位id）
     * @return 库位中该商品数量
     */
    @RequestMapping(value = "/getStock")
    @ResponseBody
    public Integer getStock(Integer id,Integer goodsId){
        Set<Tuple> tuples = redisService.getWarehouseStock(id);
        Integer number = 0;
        for (Tuple tuple : tuples){
            if(Integer.parseInt(tuple.getElement()) == goodsId){
                number = (int)tuple.getScore();
            }
        }
        return number;
    }

    /**
     * controller内部通用方法
     * 根据id查询商品类别并输出名称
     * 2022.4.21
     */
    public String productName(Integer id){
        /**
         * redis查询商品分类
         * @param id
         * @return category
         */
        String category = redisService.getGoodsCategory(id);
        /**
         * 实例通用对象用于输出
         */
        /**
         * 根据商品类至对应商品表查询商品详细信息并转移输出对象
         * @param category
         * @return goodsCom
         */
        switch (category){
            case "motherboard":
                return motherboardService.queryMotherboardById(id).getName();
            case "cpu":
                return  cpuService.queryCpuById(id).getName();
            case "graphicscard":
                return graphicscardService.queryGraphicscardById(id).getName();
            case "ram":
                return ramService.queryRamById(id).getName();
            case "ssd":
                return ssdService.querySsdById(id).getName();
            case "heatsink":
                return heatsinkService.queryHeatsinkById(id).getName();
            case "power":
                return powerService.queryPowerById(id).getName();
            case "case":
                return caseService.queryCaseById(id).getName();
            default: return null;
        }
    }

    @Resource
    private IMotherboardService motherboardService;

    @Resource
    private ICpuService cpuService;

    @Resource
    private IGraphicscardService graphicscardService;

    @Resource
    private IRamService ramService;

    @Resource
    private ISsdService ssdService;

    @Resource
    private IHeatsinkService heatsinkService;

    @Resource
    private IPowerService powerService;

    @Resource
    private ICaseService caseService;

}

