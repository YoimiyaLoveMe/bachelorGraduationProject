package com.example.onlineshop.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.onlineshop.entity.OrderRetail;

import java.util.List;


/**
 * @author wu
 * 2022.4.10
 */
public interface IOrderRetailService extends IService<OrderRetail> {
    public IPage<OrderRetail> selectOrderRetailPage(int pageNum, int pageSize, String param);
    public int addOrderRetail(OrderRetail orderretail);
    public int editOrderRetail(OrderRetail orderretail);
    public int delOrderRetailById(Integer orderretail_id);
    public List<OrderRetail> queryOrderRetailList();
    public OrderRetail queryOrderRetailById(Integer orderretail_id);

    public int lodelOrderRetailById(Integer id);

    public int payOrderRetailById(Integer id);

    }


