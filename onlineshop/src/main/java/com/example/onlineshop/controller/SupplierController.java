package com.example.onlineshop.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.onlineshop.common.ResultMapUtil;
import com.example.onlineshop.entity.Supplier;
import com.example.onlineshop.entity.Worker;
import com.example.onlineshop.service.ISupplierService;
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
 * 供货商管理controller
 * @author wu
 * 2022.4.16
 */
@Controller
@RequestMapping(value = "/supplier")

public class SupplierController {

    @Resource
    private ISupplierService supplierService;

    @RequestMapping
    public String supplier(){
        Set<String> set = redisService.getAllRole(((Worker) SecurityUtils.getSubject().getPrincipal()).getJob());
        if(set.contains("supplier")){
            return "/supplier";
        }
        else {
            return "/stop";
        }
    }

    /**
     * 转向新增页面
     */
    @RequestMapping(value="/supplierPage")
    public String register(){
        return "/supplierPage";
    }

    @Resource
    private RedisService redisService;

    /**
     * 向数据库添加数据
     */
    @RequestMapping(value = "/supplierAdd")
    @ResponseBody
    public Object supplierAdd(Supplier supplier){
        try{
            supplier.setId(redisService.getSupplierId());
            int i = supplierService.addSupplier(supplier);
            ResultMapUtil.getHashMapLogin("添加成功","1");
            return ResultMapUtil.getHashMapSave(i);
        }catch (Exception e){
            return ResultMapUtil.getHashMapLogin("添加失败","4");
//            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 逻辑删除数据
     */
    @RequestMapping(value = "/supplierDelById")
    @ResponseBody
    public Object supplierDelById(Integer id){
        try{
            int i = supplierService.lodelSupplierById(id);
            return ResultMapUtil.getHashMapSave(i);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 获取供货商对象
     */
    @RequestMapping(value="/supplierGetById")
    @ResponseBody
    public Object getSupplier(@RequestParam(name="id")Integer id){
        Supplier supplier = supplierService.querySupplierById(id);
        return supplier;
    }

    /**
     * 转向修改页面
     */
    @RequestMapping(value="/supplierQueryById")
    public String supplierInfo(@RequestParam(name="id")Integer id,Model model){
        Supplier supplier = supplierService.querySupplierById(id);
        model.addAttribute("obj",supplier);
        return "/supplierPage";
    }

    /**
     * 修改数据
     */
    @RequestMapping(value = "/supplierEdit")
    @ResponseBody
    public Object supplierEdit(Supplier supplier){
        try{
            int i = supplierService.editSupplier(supplier);
            return ResultMapUtil.getHashMapSave(i);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 分页查询列表
     * 非返回页面
     */
    @RequestMapping(value = "/supplierQueryPage")
    @ResponseBody
    public Object supplierQueryPage(@RequestParam(defaultValue = "1")int page, @RequestParam(defaultValue = "10")int limit, String param){
        try {
            IPage<Supplier> iPage = supplierService.selectSupplierPage(page,limit,param);
            return ResultMapUtil.getHashMapMysqlPage(iPage);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 获取所有信息并返回列表格式
     */
    @RequestMapping(value = "/supplierList")
    @ResponseBody
    public Object supplierList(){
        List<Supplier> supplierList = supplierService.querySupplierList();
        return ResultMapUtil.getHashMapList(supplierList);
    }

}

