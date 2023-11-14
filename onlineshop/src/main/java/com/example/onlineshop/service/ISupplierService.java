package com.example.onlineshop.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.onlineshop.entity.Supplier;

import java.util.List;


/**
 * @author wu
 * 2022.4.16
 */
public interface ISupplierService extends IService<Supplier> {
    public IPage<Supplier> selectSupplierPage(int pageNum, int pageSize, String param);
    public int addSupplier(Supplier supplier);
    public int editSupplier(Supplier supplier);
    public int delSupplierById(Integer supplier_id);
    public List<Supplier> querySupplierList();
    public Supplier querySupplierById(Integer supplier_id);

    public int lodelSupplierById(Integer id);

    }


