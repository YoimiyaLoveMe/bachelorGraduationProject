package com.example.onlineshop.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.onlineshop.entity.Cpu;

import java.util.List;


/**
 * @author wu
 * 2022.3.28
 */
public interface ICpuService extends IService<Cpu> {
    public IPage<Cpu> selectCpuPage(int pageNum, int pageSize, String param);
    public int addCpu(Cpu cpu);
    public int editCpu(Cpu cpu);
    public int delCpuById(Integer cpu_id);
    public List<Cpu> queryCpuList();
    public Cpu queryCpuById(Integer cpu_id);

    public int lodelCpuById(Integer id);
    }


