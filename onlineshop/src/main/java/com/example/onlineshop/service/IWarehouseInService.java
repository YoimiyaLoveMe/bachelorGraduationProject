package com.example.onlineshop.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.onlineshop.entity.WarehouseIn;

import java.util.List;


/**
 * @author wu
 * 2022.4.23
 */
public interface IWarehouseInService extends IService<WarehouseIn> {
    public IPage<WarehouseIn> selectWarehouseInPage(int pageNum, int pageSize, String param);
    public int addWarehouseIn(WarehouseIn warehouseIn);
    public int editWarehouseIn(WarehouseIn warehouseIn);
    public int delWarehouseInById(Integer warehouseIn_id);
    public List<WarehouseIn> queryWarehouseInList();
    public WarehouseIn queryWarehouseInById(Integer warehouseIn_id);

    public int lodelWarehouseInById(Integer id);

    }


