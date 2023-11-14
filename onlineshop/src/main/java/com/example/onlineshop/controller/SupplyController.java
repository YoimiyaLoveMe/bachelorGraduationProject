package com.example.onlineshop.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.onlineshop.common.ResultMapUtil;
import com.example.onlineshop.entity.Worker;
import com.example.onlineshop.entity.Supply;
import com.example.onlineshop.service.ISupplyService;
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
 * 供货管理controller
 * @author wu
 * 2022.4.17
 */
@Controller
@RequestMapping(value = "/supply")

public class SupplyController {

    @Resource
    private ISupplyService supplyService;

    @RequestMapping
    public String supply(){
        Set<String> set = redisService.getAllRole(((Worker) SecurityUtils.getSubject().getPrincipal()).getJob());
        if(set.contains("supply")){
            return "/supply";
        }
        else {
            return "/stop";
        }
    }

    /**
     * 转向新增页面
     */
    @RequestMapping(value="/supplyPage")
    public String toSupplyAdd(){
        return "/supplyPage";
    }

    @Resource
    private RedisService redisService;

    /**
     * 向数据库添加数据
     */
    @RequestMapping(value = "/supplyAdd")
    @ResponseBody
    public Object supplyAdd(Supply supply){
        try{
            //自动设置id
            supply.setId(redisService.getSupplyId());

            //自动设置添加者
            Worker worker = (Worker) SecurityUtils.getSubject().getPrincipal();
            supply.setEditor(worker.getId());

            int i = supplyService.addSupply(supply);
            return ResultMapUtil.getHashMapSave(i);
        }catch (Exception e){
            return ResultMapUtil.getHashMapLogin("错误","2");
            //return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 删除数据
     */
    @RequestMapping(value = "/supplyDelById")
    @ResponseBody
    public Object supplyDelById(Integer supply_id){

        try{
            int i = supplyService.delSupplyById(supply_id);
            return ResultMapUtil.getHashMapSave(i);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 获取供货对象
     */
    @RequestMapping(value="/supplyGetById")
    @ResponseBody
    public Object getSupply(Integer id){
        Supply supply = supplyService.querySupplyById(id);
        return supply;
    }

    /**
     * 转向修改页面
     */
    @RequestMapping(value="/supplyQueryById")
    public String supplyInfo(@RequestParam(name="id")Integer id,Model model){
        Supply supply = supplyService.querySupplyById(id);
        model.addAttribute("obj",supply);
        return "/supplyPage";
    }

    /**
     * 修改数据
     */
    @RequestMapping(value = "/supplyEdit")
    @ResponseBody
    public Object supplyEdit(Supply supply){
        try{
            //自动更新编辑者
            Worker worker = (Worker) SecurityUtils.getSubject().getPrincipal();
            supply.setEditor(worker.getId());

            int i = supplyService.editSupply(supply);
            return ResultMapUtil.getHashMapSave(i);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 分页查询列表
     * 非返回页面
     */
    @RequestMapping(value = "/supplyQueryPage")
    @ResponseBody
    public Object supplyQueryPage(@RequestParam(defaultValue = "1")int page, @RequestParam(defaultValue = "10")int limit, String param){
        try {
            IPage<Supply> iPage = supplyService.selectSupplyPage(page,limit,param);
            return ResultMapUtil.getHashMapMysqlPage(iPage);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 获取所有信息并返回列表格式
     */
    @RequestMapping(value = "/supplyList")
    @ResponseBody
    public Object supplyList(){
        List<Supply> supplyList = supplyService.querySupplyList();
        return ResultMapUtil.getHashMapList(supplyList);
    }
}

