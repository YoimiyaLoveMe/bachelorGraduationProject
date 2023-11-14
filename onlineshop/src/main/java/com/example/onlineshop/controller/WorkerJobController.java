package com.example.onlineshop.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.onlineshop.common.ResultMapUtil;
import com.example.onlineshop.entity.AIAC;
import com.example.onlineshop.entity.Worker;
import com.example.onlineshop.entity.WorkerJob;
import com.example.onlineshop.service.IRamService;
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
import java.lang.reflect.Array;
import java.util.List;
import java.util.Set;


/**
 * 职位管理controller
 * @author wu
 * 2022.4.12
 */
@Controller
@RequestMapping(value = "/job")

public class WorkerJobController {

    /**
     * 员工职位职权相关的controller
     */
    @Resource
    private IWorkerJobService workerJobService;

    @RequestMapping
    public String job(){
        Set<String> set = redisService.getAllRole(((Worker) SecurityUtils.getSubject().getPrincipal()).getJob());
        if(set.contains("job")){
            return "/job";
        }
        else {
            return "/stop";
        }
    }

    /**
     * 转向新增页面
     */
    @RequestMapping(value="/jobPage")
    public String tojobAdd(){
        return "/jobPage";
    }

    @Resource
    private RedisService redisService;

    /**
     * 向数据库添加数据
     */
    @RequestMapping(value = "/jobAdd")
    @ResponseBody
    public Object jobAdd(WorkerJob workerJob){
        try{
            //自动添加id
            workerJob.setId(redisService.getJobId());
            //自动添加编辑者
            Worker worker = (Worker) SecurityUtils.getSubject().getPrincipal();
            workerJob.setEditor(worker.getId());

            int i = workerJobService.addWorkerJob(workerJob);
            return ResultMapUtil.getHashMapSave(i);
        }catch (Exception e){
            return ResultMapUtil.getHashMapLogin("错误","2");
            //return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 删除数据
     */
    @RequestMapping(value = "/jobDelById")
    @ResponseBody
    public Object jobDelById(Integer job_id){

        try{
            int i = workerJobService.delWorkerJobById(job_id);
            return ResultMapUtil.getHashMapSave(i);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 获取职位对象
     */
    @RequestMapping(value="/jobGetById")
    @ResponseBody
    public Object getJob(Integer id){
        WorkerJob workerJob = workerJobService.queryWorkerJobById(id);
        return workerJob;
    }

    /**
     * 转向修改页面
     */
    @RequestMapping(value="/jobQueryById")
    public String jobInfo(@RequestParam(name="id")Integer id,Model model){
        WorkerJob workerJob = workerJobService.queryWorkerJobById(id);
        model.addAttribute("obj",workerJob);
        return "/jobPage";
    }

    /**
     * 修改数据
     */
    @RequestMapping(value = "/jobEdit")
    @ResponseBody
    public Object jobEdit(WorkerJob workerJob){
        try{
            //自动更新编辑者
            Worker worker = (Worker) SecurityUtils.getSubject().getPrincipal();
            workerJob.setEditor(worker.getId());

            int i = workerJobService.editWorkerJob(workerJob);
            return ResultMapUtil.getHashMapSave(i);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 转向职权修改页面
     */
    @RequestMapping(value="/jobRoleQueryById")
    public String jobRole(@RequestParam(name="id")Integer id,Model model){
        Set<String> set = redisService.getAllRole(id);
        model.addAttribute("set",set);
        return "/jobRole";
    }

    /**
     * 职权修改
     * @param id
     * @param list
     * @return
     */
    @RequestMapping(value = "/roleEdit")
    @ResponseBody
    public Object roleEdit(Integer id, String[] list){
        try {
            redisService.setJobRole(id, list);
            return ResultMapUtil.getHashMapSave(1);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 分页查询列表
     * 非返回页面
     */
    @RequestMapping(value = "/jobQueryPage")
    @ResponseBody
    public Object jobQueryPage(@RequestParam(defaultValue = "1")int page, @RequestParam(defaultValue = "10")int limit, String param){
        try {
            IPage<WorkerJob> iPage = workerJobService.selectWorkerJobPage(page,limit,param);
            return ResultMapUtil.getHashMapMysqlPage(iPage);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 获取所有信息并返回列表格式
     */
    @RequestMapping(value = "/jobList")
    @ResponseBody
    public Object jobList(){
        List<WorkerJob> workerJobs = workerJobService.queryWorkerJobList();
        return ResultMapUtil.getHashMapList(workerJobs);
    }
}

