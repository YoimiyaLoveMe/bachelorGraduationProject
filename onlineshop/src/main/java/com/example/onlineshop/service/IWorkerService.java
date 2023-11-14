package com.example.onlineshop.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.onlineshop.entity.Cpu;
import com.example.onlineshop.entity.Worker;

import java.util.List;

/**
 * 用户表的service接口
 * @author wu
 * 2022.3
 */
public interface IWorkerService extends IService<Worker> {

    public IPage<Worker> selectWorkerPage(int pageNum, int pageSize, String param);
    public List<Worker> queryWorkerList();

    Worker queryWorkerByWorkerId(Worker worker);
    public int addWorker(Worker worker);
    public int editWorker(Worker worker);
    public Worker queryWorkerById(String id);

    public int lodelWorkerById(String id);

    public int clockin(String id);
    public int clockout(String id);
}
