package com.example.onlineshop.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.onlineshop.common.ResultMapUtil;
import com.example.onlineshop.entity.Worker;
import com.example.onlineshop.entity.WorkerJob;
import com.example.onlineshop.service.IWorkerJobService;
import com.example.onlineshop.service.IWorkerService;
import com.example.onlineshop.service.RedisService;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;


/**
 * 员工管理controller
 * @author wu
 * 2022.4.12
 */
@Controller
@RequestMapping(value = "/worker")

public class WorkController {

    @Resource
    private IWorkerService workerService;

    @Resource
    private RedisService redisService;

    @RequestMapping
    public String worker(){
        Set<String> set = redisService.getAllRole(((Worker) SecurityUtils.getSubject().getPrincipal()).getJob());
        if(set.contains("worker")){
            return "/worker";
        }
        else {
            return "/stop";
        }
    }

    /**
     * 转向新增页面
     */
    @RequestMapping(value="/register")
    public String register(){
        return "/register";
    }

    /**
     * 向数据库添加数据
     */
    @RequestMapping(value = "/workerAdd")
    @ResponseBody
    public Object workerAdd(Worker worker){
        try{
            //用账号的后6位自动填充密码并加密
            String password = worker.getId();
            worker.setPassword(DigestUtils.md5DigestAsHex(password.substring(password.length()-6).getBytes()));
            int i = workerService.addWorker(worker);
            ResultMapUtil.getHashMapLogin("添加成功","1");
            return ResultMapUtil.getHashMapSave(i);
        }catch (Exception e){
            return ResultMapUtil.getHashMapLogin("账号已存在","4");
//            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 逻辑删除数据
     */
    @RequestMapping(value = "/workerDelById")
    @ResponseBody
    public Object workerDelById(String id){
        try{
            int i = workerService.lodelWorkerById(id);
            return ResultMapUtil.getHashMapSave(i);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 转向基础信息页面(人事)
     */
    @RequestMapping(value="/workerQueryById")
    public String workerInfo(@RequestParam(name="id")String id,Model model){
        Worker worker = workerService.queryWorkerById(id);
        model.addAttribute("obj",worker);
        return "/workerInfoPage";
    }

    /**
     * 修改数据
     */
    @RequestMapping(value = "/workerEdit")
    @ResponseBody
    public Object workerEdit(Worker worker){
        try{
            int i = workerService.editWorker(worker);
            return ResultMapUtil.getHashMapSave(i);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 分页查询列表
     * 非返回页面
     */
    @RequestMapping(value = "/workerQueryPage")
    @ResponseBody
    public Object workerQueryPage(@RequestParam(defaultValue = "1")int page, @RequestParam(defaultValue = "10")int limit, String param){
        try {
            IPage<Worker> iPage = workerService.selectWorkerPage(page,limit,param);
            return ResultMapUtil.getHashMapMysqlPage(iPage);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 获取所有信息并返回列表格式
     */
    @RequestMapping(value = "/workerList")
    @ResponseBody
    public Object workerList(){
        List<Worker> workerList = workerService.queryWorkerList();
        return ResultMapUtil.getHashMapList(workerList);
    }

}

