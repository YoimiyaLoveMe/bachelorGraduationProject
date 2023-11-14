package com.example.onlineshop.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.onlineshop.entity.LogisticsIn;


import java.util.List;


/**
 * @author wu
 * 2022.4.21
 */
public interface ILogisticsInService extends IService<LogisticsIn> {
    public IPage<LogisticsIn> selectLogisticsInPage(int pageNum, int pageSize, String param);
    public int addLogisticsIn(LogisticsIn logisticsIn);
    public int editLogisticsIn(LogisticsIn logisticsIn);
    public int delLogisticsInById(Integer logisticsIn_id);
    public List<LogisticsIn> queryLogisticsInList();
    public LogisticsIn queryLogisticsInById(Integer logisticsIn_id);

    public int lodelLogisticsInById(Integer id);

    }


