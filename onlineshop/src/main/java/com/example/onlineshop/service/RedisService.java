package com.example.onlineshop.service;


import redis.clients.jedis.Tuple;

import java.util.Set;

/**
 * @author wu
 * 2022.3.28
 */
public interface RedisService {

    public int getGoodsId();

    public void setGoodsDefault(Integer id,String category);

    public String getGoodsCategory(Integer id);

    public int getJobId();

    public void setJobRole(Integer id,String[] list);

    public Boolean getJobRole(Integer id,String html);

    public Set<String> getAllRole(Integer id);

    public int getSupplierId();

    public int getSupplyId();

    public int getPurchaseId();

    public void onSale(Integer id);

    public void offSale(Integer id);

    public Boolean isSale(Integer id);

    public Integer getSaleById(Integer id);

    public int getLogisticsInId();

    public int getLogisticsOutId();

    public void setPurchaseNumber(Integer id,Integer number);

    public Integer getPurchaseNumber(Integer id);

    public Long countPurchaseNumber(Integer id,Integer number);

    public void setLogisticsNumber(Integer id,Integer number);

    public Integer getLogisticsNumber(Integer id);

    public Long countLogisticsNumber(Integer id,Integer number);

    public int getWarehouseId();

    public void setWarehouseStock(Integer warehouse_id,Integer number,Integer goods_id);

    public void setGoodsStock(Integer id,Integer number);

    public Set<Tuple> getWarehouseStock(Integer id);

    public Integer getStockById(Integer id);

    public int getWarehouseInId();

    public int getWarehouseOutId();

    public void setOutToOut(Integer id);

    public Boolean getOutToOut(Integer id);

    public void delOutToOut(Integer id);

    /************************智能装机-遗传算法************************/

    public void setBasicParameters(Integer Iteration,Integer environmentalCapacity,String Crossover,String Mutation,String techLimit,String priceRatio,String priceULimit,String priceDLimit);

    public String getIteration();
    public String getEnvironmentalCapacity();
    public String getCrossover();
    public String getMutation();
    public String getTechLimit();
    public String getPriceRatio();
    public String getPriceULimit();
    public String getPriceDLimit();
    }





