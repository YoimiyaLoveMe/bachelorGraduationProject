package com.example.onlineshop.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.onlineshop.common.ResultMapUtil;
import com.example.onlineshop.entity.LogisticsIn;
import com.example.onlineshop.entity.Worker;
import com.example.onlineshop.service.ILogisticsInService;
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
 * 收货管理controller
 * @author wu
 * 2022.4.21
 */
@Controller
@RequestMapping(value = "/logisticsIn")

public class LogisticsInController {

    @Resource
    private ILogisticsInService logisticsInService;

    @RequestMapping
    public String logisticsIn(){
        Set<String> set = redisService.getAllRole(((Worker) SecurityUtils.getSubject().getPrincipal()).getJob());
        if(set.contains("logisticsIn")){
            return "/logisticsIn";
        }
        else {
            return "/stop";
        }
    }

    /**
     * 转向新增页面
     */
    @RequestMapping(value="/logisticsInPage")
    public String toLogisticsInAdd(){
        return "/logisticsInPage";
    }

    @Resource
    private RedisService redisService;

    @Resource
    private IPurchaseService purchaseService;

    /**
     * 向数据库添加数据
     */
    @RequestMapping(value = "/logisticsInAdd")
    @ResponseBody
    public Object logisticsInAdd(LogisticsIn logisticsIn){
        try{
            //自动设置id
            logisticsIn.setId(redisService.getLogisticsInId());

            //自动设置添加者
            Worker worker = (Worker) SecurityUtils.getSubject().getPrincipal();
            logisticsIn.setWorkerId(worker.getId());

            int i = logisticsInService.addLogisticsIn(logisticsIn);

            /**
             * 对比字符串应用*.equals
             * 使用 == 可能无法判断
             */

            //统计数量,与采购单对照
            if("采购".equals(logisticsIn.getSourceCategory())){
                Long value = redisService.countPurchaseNumber(logisticsIn.getSourceId(),logisticsIn.getNumber());
                if (value <= 0){
                    //数量不大于0,采购完成
                    purchaseService.finish(logisticsIn.getSourceId());
                }
            }

            //记录收货数量用于入库统计
            redisService.setLogisticsNumber(logisticsIn.getId(),logisticsIn.getNumber());


            return ResultMapUtil.getHashMapSave(i);
        }catch (Exception e){
            return ResultMapUtil.getHashMapLogin("错误","2");
//            return ResultMapUtil.getHashMapException(e);
        }
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
    @RequestMapping(value = "/logisticsInDelById")
    @ResponseBody
    public Object logisticsInDelById(Integer id){

        try{
            int i = logisticsInService.lodelLogisticsInById(id);
            return ResultMapUtil.getHashMapSave(i);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 获取对象
     */
    @RequestMapping(value="/logisticsInGetById")
    @ResponseBody
    public Object getLogisticsIn(Integer id){
        LogisticsIn logisticsIn = logisticsInService.queryLogisticsInById(id);
        return logisticsIn;
    }

    /**
     * 转向修改页面
     */
    @RequestMapping(value="/logisticsInQueryById")
    public String logisticsInInfo(@RequestParam(name="id")Integer id,Model model){
        LogisticsIn logisticsIn = logisticsInService.queryLogisticsInById(id);
        model.addAttribute("obj",logisticsIn);
        return "/logisticsInPage";
    }

    /**
     * 修改数据
     */
    @RequestMapping(value = "/logisticsInEdit")
    @ResponseBody
    public Object logisticsInEdit(LogisticsIn logisticsIn){
        try{
            //自动更新编辑者
            Worker worker = (Worker) SecurityUtils.getSubject().getPrincipal();
            logisticsIn.setWorkerId(worker.getId());

            int i = logisticsInService.editLogisticsIn(logisticsIn);
            return ResultMapUtil.getHashMapSave(i);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 分页查询列表
     * 非返回页面
     */
    @RequestMapping(value = "/logisticsInQueryPage")
    @ResponseBody
    public Object logisticsInQueryPage(@RequestParam(defaultValue = "1")int page, @RequestParam(defaultValue = "10")int limit, String param){
        try {
            IPage<LogisticsIn> iPage = logisticsInService.selectLogisticsInPage(page,limit,param);
            return ResultMapUtil.getHashMapMysqlPage(iPage);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 获取所有信息并返回列表格式
     */
    @RequestMapping(value = "/logisticsInList")
    @ResponseBody
    public Object logisticsInList(){
        List<LogisticsIn> logisticsInList = logisticsInService.queryLogisticsInList();
        return ResultMapUtil.getHashMapList(logisticsInList);
    }
}

