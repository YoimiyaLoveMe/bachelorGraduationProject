package com.example.onlineshop.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.onlineshop.entity.WorkerJob;

import java.util.List;


/**
 * @author wu
 * 2022.3.28
 */
public interface IWorkerJobService extends IService<WorkerJob> {
    public IPage<WorkerJob> selectWorkerJobPage(int pageNum, int pageSize, String param);
    public int addWorkerJob(WorkerJob workerjob);
    public int editWorkerJob(WorkerJob workerjob);
    public int delWorkerJobById(Integer job_id);
    public List<WorkerJob> queryWorkerJobList();
    public WorkerJob queryWorkerJobById(Integer job_id);

    }


