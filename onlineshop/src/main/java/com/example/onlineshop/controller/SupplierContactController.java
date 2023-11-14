package com.example.onlineshop.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.onlineshop.common.ResultMapUtil;
import com.example.onlineshop.entity.Supplier;
import com.example.onlineshop.entity.SupplierContact;
import com.example.onlineshop.entity.Worker;
import com.example.onlineshop.service.ISupplierContactService;
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
 * 供货商联系人管理controller
 * @author wu
 * 2022.4.16
 */
@Controller
@RequestMapping(value = "/supplierContact")

public class SupplierContactController {

    @Resource
    private ISupplierContactService supplierContactService;

    @Resource
    private RedisService redisService;

    @RequestMapping
    public String supplierContact(){
        Set<String> set = redisService.getAllRole(((Worker) SecurityUtils.getSubject().getPrincipal()).getJob());
        if(set.contains("supplierContact")){
            return "/supplierContact";
        }
        else {
            return "/stop";
        }
    }

    /**
     * 转向新增页面
     */
    @RequestMapping(value="/supplierContactPage")
    public String addSupplierContact(){
        return "/supplierContactPage";
    }

    /**
     * 向数据库添加数据
     */
    @RequestMapping(value = "/supplierContactAdd")
    @ResponseBody
    public Object supplierContactAdd(SupplierContact supplierContact){
        try{
            int i = supplierContactService.addSupplierContact(supplierContact);
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
    @RequestMapping(value = "/supplierContactDelById")
    @ResponseBody
    public Object supplierContactDelById(String id){
        try{
            int i = supplierContactService.lodelSupplierContactById(id);
            return ResultMapUtil.getHashMapSave(i);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 转向修改页面
     */
    @RequestMapping(value="/supplierContactQueryById")
    public String supplierContactInfo(@RequestParam(name="id")String id,Model model){
        SupplierContact supplierContact = supplierContactService.querySupplierContactById(id);
        model.addAttribute("obj",supplierContact);
        return "/supplierContactPage";
    }

    /**
     * 修改数据
     */
    @RequestMapping(value = "/supplierContactEdit")
    @ResponseBody
    public Object supplierContactEdit(SupplierContact supplierContact){
        try{
            int i = supplierContactService.editSupplierContact(supplierContact);
            return ResultMapUtil.getHashMapSave(i);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 分页查询列表
     * 非返回页面
     */
    @RequestMapping(value = "/supplierContactQueryPage")
    @ResponseBody
    public Object supplierContactQueryPage(@RequestParam(defaultValue = "1")int page, @RequestParam(defaultValue = "10")int limit, String param){
        try {
            IPage<SupplierContact> iPage = supplierContactService.selectSupplierContactPage(page,limit,param);
            return ResultMapUtil.getHashMapMysqlPage(iPage);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 获取所有信息并返回列表格式
     */
    @RequestMapping(value = "/supplierContactList")
    @ResponseBody
    public Object supplierContactList(){
        List<SupplierContact> supplierContactList = supplierContactService.querySupplierContactList();
        return ResultMapUtil.getHashMapList(supplierContactList);
    }

}

