package com.example.onlineshop.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.onlineshop.entity.Purchase;

import java.util.List;


/**
 * @author wu
 * 2022.4.20
 */
public interface IPurchaseService extends IService<Purchase> {
    public IPage<Purchase> selectPurchasePage(int pageNum, int pageSize, String param);
    public int addPurchase(Purchase purchase);
    public int editPurchase(Purchase purchase);
    public int delPurchaseById(Integer purchase_id);
    public List<Purchase> queryPurchaseList();
    public Purchase queryPurchaseById(Integer purchase_id);

    public void lodelete(Integer id);
    public void refuse(Integer id);
    public void examine(Integer id);
    public void finish(Integer id);
    }


