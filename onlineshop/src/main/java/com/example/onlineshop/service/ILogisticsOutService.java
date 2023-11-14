package com.example.onlineshop.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.onlineshop.entity.LogisticsOut;

import java.util.List;


/**
 * @author wu
 * 2022.5.21
 */
public interface ILogisticsOutService extends IService<LogisticsOut> {
    public IPage<LogisticsOut> selectLogisticsOutPage(int pageNum, int pageSize, String param);
    public int addLogisticsOut(LogisticsOut logisticsOut);
    public int editLogisticsOut(LogisticsOut logisticsOut);
    public int delLogisticsOutById(Integer logisticsOut_id);
    public List<LogisticsOut> queryLogisticsOutList();
    public LogisticsOut queryLogisticsOutById(Integer logisticsOut_id);

    public int lodelLogisticsOutById(Integer id);

    }


