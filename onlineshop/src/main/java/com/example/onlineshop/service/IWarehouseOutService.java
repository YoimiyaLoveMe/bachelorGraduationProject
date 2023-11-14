package com.example.onlineshop.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.onlineshop.entity.WarehouseOut;

import java.util.List;


/**
 * @author wu
 * 2022.5.20
 */
public interface IWarehouseOutService extends IService<WarehouseOut> {
    public IPage<WarehouseOut> selectWarehouseOutPage(int pageNum, int pageSize, String param);
    public int addWarehouseOut(WarehouseOut warehouseOut);
    public int editWarehouseOut(WarehouseOut warehouseOut);
    public int delWarehouseOutById(Integer warehouseOut_id);
    public List<WarehouseOut> queryWarehouseOutList();
    public WarehouseOut queryWarehouseOutById(Integer warehouseOut_id);

    public int lodelWarehouseOutById(Integer id);

    }


