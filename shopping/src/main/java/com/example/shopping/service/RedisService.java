package com.example.shopping.service;


import redis.clients.jedis.Tuple;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author wu
 * 2022.4.7
 */
public interface RedisService {

    public String getGoodsCategory(Integer id);

    public Integer getSaleById(Integer id);

    public void saleChangeSales(Integer id ,Integer number);

    public Integer getStockById(Integer id);

    public void saleChangeStock(Integer id ,Integer number);

    public int getOrderId();

    public void setOrderCategory(Integer id,String category);

    public String getOrderCategory(Integer id);

    public List<String> getAddress(String id);

    public void addAddress(String id,String address,String flag);

    public void delAddress(String id,String address);

    public void setDefaultAddress(String id,String address);

    public List<String> getHotGoodsId(Integer index);

    public List<String> getNewGoodsId(Integer index);

    public List<String> getRandomGoodsId(Integer index);

    public void addLove(String customerId,Integer goodsId);

    public Long getLoveNumber(String id);

    public Set<String> getLove(String id);

    public void delLove(String customerId,Integer goodsId);

    public void addCart(String customerId,Integer goodsId,Integer number);

    public Long getCartNumber(String id);

    public Map<String,String> getCart(String id);

    public String getCartOne(String customerId,Integer goodsId);

    public void delCart(String customerId,Integer goodsId);

    public void setCombination(String customerId,String combination);

    public void delCombination(String customerId);

    public Set<String> getCombination(String id);

    }


