package com.example.onlineshop.service.impl;

import com.example.onlineshop.common.JedisUtil;
import com.example.onlineshop.service.RedisService;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.Set;

/**
 * service实现类
 * @author wu
 * 2022.3.28
 */
@Service
public class RedisServiceImpl implements RedisService {

    /**
     * 获取商品id
     * @return id
     */
    @Override
    public int getGoodsId(){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(1);
        jedis.incr("goodsid");
        Integer value =  Integer.parseInt(jedis.get("goodsid"));
        jedis.close();
        return value;
    }

    /**
     * 添加商品后redis相关数据初始化(商品库存，销量，商品类别)
     * 初始化后防止redis业务查询出错
     */
    @Override
    public void setGoodsDefault(Integer id,String category){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(1);
        //商品类别
        jedis.set("category:goodsid:"+id,category);
        //商品销售量
        jedis.zadd("salegoods",0,id.toString());
        jedis.select(2);
        //商品库存
        jedis.set("stock:goodsid:"+id, String.valueOf(0));
        jedis.close();
    }

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
     * 获取职位id
     * @return id
     */
    @Override
    public int getJobId(){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(2);
        jedis.incr("jobid");
        Integer value =  Integer.parseInt(jedis.get("jobid"));
        jedis.close();
        return value;
    }

    /**
     * @author wu
     * 2022.4.13
     * 报错信息：查询权限时，若无返回值则会在转换整型时报错
     * jedis方法返回的value几乎都是String类型，故不应采用整型相等比较，而应采用equals
     * 使用equals时，确保左侧的对象一定不为空，即使用定值对象调用equals和获取的不定对象相比较
     */

    /**
     * 设定、修改职权
     * @param id
     * @param list
     */
    @Override
    public void setJobRole(Integer id,String[] list){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(2);
        //无论是新设还是修改，均先删除原有数据
        jedis.del("role:jobid:"+id);
        for(String html : list){
            jedis.sadd("role:jobid:"+id,html);
        }
        jedis.close();
    }

    /**
     * 查询职权
     */
    @Override
    public Boolean getJobRole(Integer id, String html){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(2);
        Boolean flag = jedis.sismember("role:jobid:"+id,html);
        jedis.close();
        return flag;
    }

    /**
     * 获取所有职权
     */
    @Override
    public Set<String> getAllRole(Integer id){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(2);
        Set<String> set = jedis.smembers("role:jobid:"+id);
        jedis.close();
        return set;
    }

    /**
     * 获取供货商id
     * @return id
     */
    @Override
    public int getSupplierId(){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(2);
        jedis.incr("supplierid");
        Integer value =  Integer.parseInt(jedis.get("supplierid"));
        jedis.close();
        return value;
    }

    /**
     * 获取供货id
     * @return id
     */
    @Override
    public int getSupplyId(){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(2);
        jedis.incr("supplyid");
        Integer value =  Integer.parseInt(jedis.get("supplyid"));
        jedis.close();
        return value;
    }

    /**
     * 获取采购单id
     * @return id
     */
    @Override
    public int getPurchaseId(){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(2);
        jedis.incr("purchaseid");
        Integer value =  Integer.parseInt(jedis.get("purchaseid"));
        jedis.close();
        return value;
    }

    /**
     * 商品上架加入上架顺序列表和在售集合
     */
    @Override
    public void onSale(Integer id){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(1);
        jedis.lpush("newgoods",id.toString());
        jedis.sadd("goodsonsale",id.toString());
        jedis.close();
    }

    /**
     * 商品下架移出上架顺序列表和在售集合
     */
    @Override
    public void offSale(Integer id){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(1);
        jedis.lrem("newgoods",1,id.toString());
        jedis.srem("goodsonsale",id.toString());
        jedis.close();
    }

    /**
     * 查询商品是否在售
     */
    @Override
    public Boolean isSale(Integer id){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(1);
        Boolean flag = jedis.sismember("goodsonsale",id.toString());
        jedis.close();
        return flag;
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
     * 获取收货单id
     * @return id
     */
    @Override
    public int getLogisticsInId(){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(2);
        jedis.incr("logisticsinid");
        Integer value =  Integer.parseInt(jedis.get("logisticsinid"));
        jedis.close();
        return value;
    }

    /**
     * 获取发货单id
     * @return id
     */
    @Override
    public int getLogisticsOutId(){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(2);
        jedis.incr("logisticsoutid");
        Integer value =  Integer.parseInt(jedis.get("logisticsoutid"));
        jedis.close();
        return value;
    }

    /**
     * 设置采购商品数量(采购)
     * 用于收货时统计
     */
    @Override
    public void setPurchaseNumber(Integer id,Integer number){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(2);
        jedis.set("number:purchaseid:"+id,number.toString());
        jedis.close();
    }

    /**
     * 查询采购商品数量
     */
    @Override
    public Integer getPurchaseNumber(Integer id){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(2);
        Integer value =  Integer.parseInt(jedis.get("number:purchaseid:"+id));
        jedis.close();
        return value;
    }

    /**
     * 收货时操作采购商品数量(收货)
     */
    @Override
    public Long countPurchaseNumber(Integer id,Integer number){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(2);
        Long value = jedis.decrBy("number:purchaseid:"+id,number);
        jedis.close();
        return value;
    }

    /**
     * 设置收货商品数量(收货)
     * 用于入库时统计
     */
    @Override
    public void setLogisticsNumber(Integer id,Integer number){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(2);
        jedis.set("number:logisticsinid:"+id,number.toString());
        jedis.close();
    }

    /**
     * 查询收货商品数量
     */
    @Override
    public Integer getLogisticsNumber(Integer id){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(2);
        Integer value =  Integer.parseInt(jedis.get("number:logisticsinid:"+id));
        jedis.close();
        return value;
    }

    /**
     * 入库时操作收货商品数量(入库)
     */
    @Override
    public Long countLogisticsNumber(Integer id,Integer number){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(2);
        Long value = jedis.decrBy("number:logisticsinid:"+id,number);
        jedis.close();
        return value;
    }

    /**
     * 获取库位id
     * @return id
     */
    @Override
    public int getWarehouseId(){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(2);
        jedis.incr("warehouseid");
        Integer value =  Integer.parseInt(jedis.get("warehouseid"));
        jedis.close();
        return value;
    }

    /**
     * 商品入库
     * 设置库位库存
     */
    @Override
    public void setWarehouseStock(Integer warehouse_id,Integer number,Integer goods_id){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(2);
        Double stock = Double.valueOf(number);
        jedis.zincrby("stock:warehouseid:"+warehouse_id,stock,goods_id.toString());
        jedis.close();
    }

    /**
     * 商品入库
     * 设置商品库存
     */
    @Override
    public void setGoodsStock(Integer id,Integer number){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(2);
        jedis.incrBy("stock:goodsid:"+id,number);
        jedis.close();
    }

    /**
     * 获取库位库存
     */
    @Override
    public Set<Tuple> getWarehouseStock(Integer id){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(2);
        Set<Tuple> tuples = jedis.zrevrangeWithScores("stock:warehouseid:"+id,0,-1);
        jedis.close();
        return tuples;
    }

    /**
     * 获取商品库存
     */
    @Override
    public Integer getStockById(Integer id){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(2);
        Integer value = Integer.parseInt(jedis.get("stock:goodsid:"+id));
        jedis.close();
        return value;
    }

    /**
     * 获取入库单id
     * @return id
     */
    @Override
    public int getWarehouseInId(){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(2);
        jedis.incr("warehouseinid");
        Integer value =  Integer.parseInt(jedis.get("warehouseinid"));
        jedis.close();
        return value;
    }

    /**
     * 获取出库单id
     * @return id
     */
    @Override
    public int getWarehouseOutId(){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(2);
        jedis.incr("warehouseoutid");
        Integer value =  Integer.parseInt(jedis.get("warehouseoutid"));
        jedis.close();
        return value;
    }

    /**
     * 出库添加至待发货集合
     */
    @Override
    public void setOutToOut(Integer id){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(2);
        jedis.sadd("outtoout",id.toString());
        jedis.close();
    }

    /**
     * 验证出库单是否未发货
     */
    @Override
    public Boolean getOutToOut(Integer id){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(2);
        Boolean flag = jedis.sismember("outtoout",id.toString());
        jedis.close();
        return flag;
    }

    /**
     * 发货时从集合删除出库单
     */
    @Override
    public void delOutToOut(Integer id){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(2);
        jedis.srem("outtoout",id.toString());
        jedis.close();
    }


    /*******************************************************************************************************************/

    /**
     * 设定基础参数
     */
    @Override
    public void setBasicParameters(Integer Iteration,Integer environmentalCapacity,String Crossover,String Mutation,String techLimit,String priceRatio,String priceULimit,String priceDLimit){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(3);
        jedis.set("Iteration",Iteration.toString());
        jedis.set("environmentalCapacity",environmentalCapacity.toString());
        jedis.set("Crossover",Crossover);
        jedis.set("Mutation",Mutation);
        jedis.set("techLimit",techLimit);
        jedis.set("priceRatio",priceRatio);
        jedis.set("priceULimit",priceULimit);
        jedis.set("priceDLimit",priceDLimit);
        jedis.close();
    }

    @Override
    public String getIteration(){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(3);
        String Iteration = jedis.get("Iteration");
        jedis.close();
        return Iteration;
    }

    @Override
    public String getEnvironmentalCapacity(){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(3);
        String environmentalCapacity = jedis.get("environmentalCapacity");
        jedis.close();
        return environmentalCapacity;
    }

    @Override
    public String getCrossover(){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(3);
        String Crossover = jedis.get("Crossover");
        jedis.close();
        return Crossover;
    }

    @Override
    public String getMutation(){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(3);
        String Mutation = jedis.get("Mutation");
        jedis.close();
        return Mutation;
    }

    @Override
    public String getTechLimit(){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(3);
        String techLimit = jedis.get("techLimit");
        jedis.close();
        return techLimit;
    }

    @Override
    public String getPriceRatio(){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(3);
        String priceRatio = jedis.get("priceRatio");
        jedis.close();
        return priceRatio;
    }

    @Override
    public String getPriceULimit(){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(3);
        String priceULimit = jedis.get("priceULimit");
        jedis.close();
        return priceULimit;
    }

    @Override
    public String getPriceDLimit(){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(3);
        String priceDLimit = jedis.get("priceDLimit");
        jedis.close();
        return priceDLimit;
    }

}

