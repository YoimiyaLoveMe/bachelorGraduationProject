package com.example.onlineshop.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.onlineshop.entity.SupplierContact;

import java.util.List;


/**
 * @author wu
 * 2022.4.16
 */
public interface ISupplierContactService extends IService<SupplierContact> {
    public IPage<SupplierContact> selectSupplierContactPage(int pageNum, int pageSize, String param);
    public int addSupplierContact(SupplierContact supplierContact);
    public int editSupplierContact(SupplierContact supplierContact);
    public int delSupplierContactById(String supplierContact_id);
    public List<SupplierContact> querySupplierContactList();
    public SupplierContact querySupplierContactById(String supplierContact_id);

    public int lodelSupplierContactById(String id);

    }


