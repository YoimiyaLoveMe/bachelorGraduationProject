package com.example.shopping.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.shopping.entity.OrderCombination;

import java.util.List;


/**
 * @author wu
 * 2022.4.18
 */
public interface IOrderCombinationService extends IService<OrderCombination> {
    public IPage<OrderCombination> selectOrderCombinationPage(int pageNum, int pageSize, String param);
    public int addOrderCombination(OrderCombination orderCombination);
    public int editOrderCombination(OrderCombination orderCombination);
    public int delOrderCombinationById(Integer orderCombination_id);
    public List<OrderCombination> queryOrderCombinationList();
    public OrderCombination queryOrderCombinationById(Integer orderCombination_id);

    public int lodelOrderCombinationById(Integer id);

    public int payOrderCombinationById(Integer id);

    public int cancelOrderCombinationById(Integer id);
    }


