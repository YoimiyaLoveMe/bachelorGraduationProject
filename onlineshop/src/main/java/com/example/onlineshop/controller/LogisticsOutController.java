package com.example.onlineshop.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.onlineshop.common.ResultMapUtil;
import com.example.onlineshop.entity.LogisticsOut;
import com.example.onlineshop.entity.OrderRetail;
import com.example.onlineshop.entity.Worker;
import com.example.onlineshop.service.*;
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
 * 发货管理controller
 * @author wu
 * 2022.5.21
 */
@Controller
@RequestMapping(value = "/logisticsOut")

public class LogisticsOutController {

    @Resource
    private ILogisticsOutService logisticsOutService;

    @RequestMapping
    public String logisticsOut(){
        Set<String> set = redisService.getAllRole(((Worker) SecurityUtils.getSubject().getPrincipal()).getJob());
        if(set.contains("logisticsOut")){
            return "/logisticsOut";
        }
        else {
            return "/stop";
        }
    }

    /**
     * 转向新增页面
     */
    @RequestMapping(value="/logisticsOutPage")
    public String toLogisticsOutAdd(){
        return "/logisticsOutPage";
    }

    @Resource
    private RedisService redisService;

    @Resource
    private IOrderRetailService OrderRetailService;

    @Resource
    private IWarehouseOutService warehouseOutService;

    /**
     * 向数据库添加数据
     */
    @RequestMapping(value = "/logisticsOutAdd")
    @ResponseBody
    public Object logisticsOutAdd(LogisticsOut logisticsOut){
        try{
            //自动设置id
            logisticsOut.setId(redisService.getLogisticsOutId());

            //自动设置添加者
            Worker worker = (Worker) SecurityUtils.getSubject().getPrincipal();
            logisticsOut.setWorkerId(worker.getId());

            int i = logisticsOutService.addLogisticsOut(logisticsOut);

            //将销售订单设置为“已发货”
            OrderRetail orderRetail = OrderRetailService.queryOrderRetailById((warehouseOutService.queryWarehouseOutById(logisticsOut.getSourceId())).getSourceId());
            orderRetail.setState("已发货");
            OrderRetailService.editOrderRetail(orderRetail);
            //从待发货集合中删除出库单
            redisService.delOutToOut(logisticsOut.getSourceId());

            return ResultMapUtil.getHashMapSave(i);
        }catch (Exception e){
            return ResultMapUtil.getHashMapLogin("错误","2");
//            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 查询出库单是否待发货
     */
    @RequestMapping(value = "/getOutToOut")
    @ResponseBody
    public Boolean getOutToOut(Integer id){
        return redisService.getOutToOut(id);
    }


    /**
     * 查询收货商品的入库数量
     */
    @RequestMapping(value = "/getNumber")
    @ResponseBody
    public Integer getPurchaseNumber(Integer id){
        return redisService.getLogisticsNumber(id);
    }


    /**
     * 删除数据
     */
    @RequestMapping(value = "/logisticsOutDelById")
    @ResponseBody
    public Object logisticsOutDelById(Integer id){

        try{
            int i = logisticsOutService.lodelLogisticsOutById(id);
            return ResultMapUtil.getHashMapSave(i);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 获取对象
     */
    @RequestMapping(value="/logisticsOutGetById")
    @ResponseBody
    public Object getLogisticsOut(Integer id){
        LogisticsOut logisticsOut = logisticsOutService.queryLogisticsOutById(id);
        return logisticsOut;
    }

    /**
     * 转向修改页面
     */
    @RequestMapping(value="/logisticsOutQueryById")
    public String logisticsOutInfo(@RequestParam(name="id")Integer id,Model model){
        LogisticsOut logisticsOut = logisticsOutService.queryLogisticsOutById(id);
        model.addAttribute("obj",logisticsOut);
        return "/logisticsOutPage";
    }

    /**
     * 修改数据
     */
    @RequestMapping(value = "/logisticsOutEdit")
    @ResponseBody
    public Object logisticsOutEdit(LogisticsOut logisticsOut){
        try{
            //自动更新编辑者
            Worker worker = (Worker) SecurityUtils.getSubject().getPrincipal();
            logisticsOut.setWorkerId(worker.getId());

            int i = logisticsOutService.editLogisticsOut(logisticsOut);
            return ResultMapUtil.getHashMapSave(i);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 分页查询列表
     * 非返回页面
     */
    @RequestMapping(value = "/logisticsOutQueryPage")
    @ResponseBody
    public Object logisticsOutQueryPage(@RequestParam(defaultValue = "1")int page, @RequestParam(defaultValue = "10")int limit, String param){
        try {
            IPage<LogisticsOut> iPage = logisticsOutService.selectLogisticsOutPage(page,limit,param);
            return ResultMapUtil.getHashMapMysqlPage(iPage);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 获取所有信息并返回列表格式
     */
    @RequestMapping(value = "/logisticsOutList")
    @ResponseBody
    public Object logisticsOutList(){
        List<LogisticsOut> logisticsOutList = logisticsOutService.queryLogisticsOutList();
        return ResultMapUtil.getHashMapList(logisticsOutList);
    }
}

