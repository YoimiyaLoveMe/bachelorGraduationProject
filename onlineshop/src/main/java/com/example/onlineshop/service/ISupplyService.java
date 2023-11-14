package com.example.onlineshop.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.onlineshop.entity.Supply;

import java.util.List;


/**
 * @author wu
 * 2022.4.17
 */
public interface ISupplyService extends IService<Supply> {
    public IPage<Supply> selectSupplyPage(int pageNum, int pageSize, String param);
    public int addSupply(Supply supply);
    public int editSupply(Supply supply);
    public int delSupplyById(Integer supply_id);
    public List<Supply> querySupplyList();
    public Supply querySupplyById(Integer supply_id);
    }


