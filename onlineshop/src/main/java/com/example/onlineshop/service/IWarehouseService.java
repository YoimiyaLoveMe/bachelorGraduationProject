package com.example.onlineshop.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.onlineshop.entity.Warehouse;

import java.util.List;


/**
 * @author wu
 * 2022.4.20
 */
public interface IWarehouseService extends IService<Warehouse> {
    public IPage<Warehouse> selectWarehousePage(int pageNum, int pageSize, String param);
    public int addWarehouse(Warehouse warehouse);
    public int editWarehouse(Warehouse warehouse);
    public int delWarehouseById(Integer warehouse_id);
    public List<Warehouse> queryWarehouseList();
    public Warehouse queryWarehouseById(Integer warehouse_id);
    }


