package com.example.shopping.service.impl;

import com.baomidou.mybatisplus.extension.api.R;
import com.example.shopping.common.JedisUtil;
import com.example.shopping.service.RedisService;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.math.BigDecimal;
import java.util.*;

/**
 * service实现类
 * @author wu
 * 2022.4.7
 */
@Service
public class RedisServiceImpl implements RedisService {

    /**
     * 获取商品类别
     * @param id
     * @return category
     */
    @Override
    public String getGoodsCategory(Integer id){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(1);
        String value =  jedis.get("category:goodsid:"+id);
        jedis.close();
        return value;
    }

    /**
     * 获取商品销量
     * @param id
     * @return sale
     */
    @Override
    public Integer getSaleById(Integer id){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(1);
        //Double对象转化为int
        Integer sale = jedis.zscore("salegoods",id.toString()).intValue();
        jedis.close();
        return sale;
    }

    /**
     * 销售修改商品销量
     * @param id
     * @return stock
     */
    @Override
    public void saleChangeSales(Integer id,Integer number){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(1);
        Double sales = Double.valueOf(number);
        jedis.zincrby("salegoods",sales,id+"");
        jedis.close();
    }

    /**
     * 获取商品库存
     * @param id
     * @return stock
     */
    @Override
    public Integer getStockById(Integer id){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(2);
        //Double对象转化为int
        Integer stock = Integer.parseInt(jedis.get("stock:goodsid:"+id));
        jedis.close();
        return stock;
    }

    /**
     * 销售修改商品库存
     * @param id
     * @return stock
     */
    @Override
    public void saleChangeStock(Integer id,Integer number){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(2);
        jedis.decrBy("stock:goodsid:"+id,(long)number);
        jedis.close();
    }

    /**
     * 获取订单id
     */
    @Override
    public int getOrderId(){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(1);
        jedis.incr("orderid");
        Integer value =  Integer.parseInt(jedis.get("orderid"));
        jedis.close();
        return value;
    }

    /**
     * 创建订单时记录订单类别
     */
    @Override
    public void setOrderCategory(Integer id,String category){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(1);
        jedis.set("category:orderid:"+id,category);
        jedis.close();
    }

    /**
     * 付款时获取订单类别
     */
    @Override
    public String getOrderCategory(Integer id){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(1);
        String value = jedis.get("category:orderid:"+id);
        jedis.close();
        return value;
    }

    /**
     * 获取用户收货地址列表
     */
    @Override
    public List<String> getAddress(String id){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(1);
        List<String> list = jedis.lrange("address:customerid:"+id,0,-1);
        jedis.close();
        return list;
    }

    /**
     * 添加收货地址
     */
    @Override
    public void addAddress(String id,String address,String flag){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(1);
        if ("on".equals(flag)) {
            //设为默认
            jedis.lpush("address:customerid:"+id,address);
        }else {
            jedis.rpush("address:customerid:"+id,address);
        }
        jedis.close();
    }

    /**
     * 删除地址
     */
    @Override
    public void delAddress(String id,String address){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(1);
        jedis.lrem("address:customerid:"+id,1,address);
        jedis.close();
    }

    /**
     * 设置默认地址
     */
    @Override
    public void setDefaultAddress(String id,String address){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(1);
        //先移除再左进
        jedis.lrem("address:customerid:"+id,1,address);
        jedis.lpush("address:customerid:"+id,address);
        jedis.close();
    }

    /**
     * 获取销量前列商品
     */
    @Override
    public List<String> getHotGoodsId(Integer index){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(1);
        List list = new ArrayList();
        for(int i=0;i<index;){
            Set<String> set = jedis.zrevrange("salegoods",i,i);
            for (String member: set){
                //验证是否在售
                if (jedis.sismember("goodsonsale",member)){
                    i+=1;
                    list.add(member);
                }
            }
        }
        jedis.close();
        return list;
    }

    /**
     * 获取新上架商品
     */
    @Override
    public List<String> getNewGoodsId(Integer index){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(1);
        List<String> set = jedis.lrange("newgoods",0,index);
        jedis.close();
        return set;
    }

    /**
     * 获取随机在售商品
     */
    @Override
    public List<String> getRandomGoodsId(Integer index){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(1);
        List<String> list = jedis.srandmember("goodsonsale",index);
        jedis.close();
        return list;
    }

    /**
     * 添加心愿单
     */
    @Override
    public void addLove(String customerId,Integer goodsId){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(1);
        jedis.sadd("love:customerid:"+customerId,goodsId.toString());
        jedis.close();
    }

    /**
     * 取消收藏,商品移出心愿单
     */
    @Override
    public void delLove(String customerId,Integer goodsId){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(1);
        jedis.srem("love:customerid:"+customerId,goodsId.toString());
        jedis.close();
    }

    /**
     * 获得心愿商品总数量
     */
    @Override
    public Long getLoveNumber(String id){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(1);
        Long value = jedis.scard("love:customerid:"+id);
        jedis.close();
        return value;
    }

    /**
     * 获取心愿单所有商品
     */
    @Override
    public Set<String> getLove(String id){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(1);
        Set<String> set = jedis.smembers("love:customerid:"+id);
        jedis.close();
        return set;
    }

    /**
     * 添加购物车（也用来操作购物车商品数量）
     */
    @Override
    public void addCart(String customerId,Integer goodsId,Integer number){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(1);
        jedis.hincrBy("cart:customerid:"+customerId,goodsId.toString(),number);
        jedis.close();
    }

    /**
     * 删除购物车商品
     */
    @Override
    public void delCart(String customerId,Integer goodsId){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(1);
        jedis.hdel("cart:customerid:"+customerId,goodsId.toString());
        jedis.close();
    }

    /**
     * 获得购物车商品总数量
     */
    @Override
    public Long getCartNumber(String id){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(1);
        Long value = jedis.hlen("cart:customerid:"+id);
        jedis.close();
        return value;
    }

    /**
     * 获取购物车所有商品
     */
    @Override
    public Map<String,String> getCart(String id){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(1);
        Map<String,String> map = jedis.hgetAll("cart:customerid:"+id);
        jedis.close();
        return map;
    }

    /**
     * 查询购物车某个商品数量
     */
    @Override
    public String getCartOne(String customerId, Integer goodsId){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(1);
        String value = jedis.hget("cart:customerid:"+customerId,goodsId.toString());
        jedis.close();
        return value;
    }

    /**
     * 添加整机配置单
     */
    @Override
    public void setCombination(String customerId,String combination){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(1);
        //只允许用户同时保存一个整机配置单，再次保存删除原有配置单
        jedis.del("combination:customerid:"+customerId);
        String[] list = combination.split(",");
        for (String id : list){
            jedis.sadd("combination:customerid:"+customerId,id);
        }
        jedis.close();
    }

    /**
     * 获取整机配置单
     */
    @Override
    public Set<String> getCombination(String id){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(1);
        Set<String> set = jedis.smembers("combination:customerid:"+id);
        jedis.close();
        return set;
    }

    /**
     * 删除整机配置单
     */
    @Override
    public void delCombination(String id){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(1);
        jedis.del("combination:customerid:"+id);
        jedis.close();
    }

}

