package com.example.shopping.common;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


/**
 * Jedis连接池工具类
 * @author wu
 * @finish 2022.3.30
 */
public class JedisUtil {
    private static JedisPool jp;
    static {
        JedisPoolConfig jpc = new JedisPoolConfig();
        jpc.setMaxTotal(30);
        jpc.setMaxIdle(10);
        jp = new JedisPool(jpc,"127.0.0.1",6379);
    }

    public static Jedis getJedis(){
        return jp.getResource();
    }
    public static void main(String[] args){
        JedisUtil.getJedis();
    }

}
/**
 * redis分库设计详情
 * @author wu
 * @update 2022.4.29
 * 0 : 测试用,随时可能清空
 * 1 : 前台商城相关
 * 1 : 商品id分配 string (key-value) : goodsid - * (incr)
 * 1 : //商品id分配 set (key-member) : goodsid - *  (获取商品id: scard goodsid +1)(随机商品推荐: srandmember goodsid [count]
 * 1 : 商品分类 string (key-value) : category:goodsid:* - * (category)
 * 1 : 商品销售量（热卖商品推荐） sorted_set (key-member-score) : salegoods - *(goodsid) - * (number.incr)
 * 1 : 商品上架顺序（新品上架推荐） list (key-value) : newgoods - *(goodsid) (左进左查)
 * 1 : 商品上架（在售集合，随机推荐） set (key-member) goodsonsale - *(goodsid)
 * 1 : 评论id分配 string (key-value) : review:goodsid - * (incr)
 * 1 : 商品评论顺序 list (key-value) : review:goodsid:* - *(customerid) (左进)
 * 1 : 商品评论内容 sorted_set (key-member-score) : review:goodsid:*:customerid:* - * - 0~5(grade)
 * 1 : 订单id分配 string (key-value) : orderid - * (incr)
 * 1 : 订单分类 string (key-value) : category:orderid:* - category/combination
 * 1 : 用户心愿单（收藏） set (key-member): love:customerid:* - *(goodsid) (差集推荐关联商品)
 * 1 : 用户购物车 hash (key-field-value): cart:customerid:* - *(goodsid) - * (number)
 * 1 : 用户整机配置单 set (key-member) : combination:customerid:* - * (goodsid)
 * 1 : 用户收货地址 list (key-value): address:customerid:* - * (address) (添加地址右进，设置默认地址先删后左进,查询地址时默认地址索引最小)
 * 2 : 后台管理相关
 * 2 : 职位id分配 string (key-value) : jobid - * (incr)
 * 2 : 职位职权 : set (key-member): role:jobid:* - *(html)
 * 2 : 供货商id分配 string (key-value) : supplierid - * (incr)
 * 2 : 供货id分配 string (key-value) : supplyid - * (incr)
 * 2 : 库位id分配 string (key-value) : warehouseid - * (incr)
 * 2 : 库位库存表 sorted_set (key-member-score): stock:warehouseid:* - *(goodsid) - *(number)
 * 2 : 商品库存 string (key-value) : stock:goodsid:* - * (number.incr)
 * 2 : 采购id分配 string (key-value) : purchaseid - * (incr)
 * 2 : 采购单商品数量（收货统计用） string (key-value) : number:purchaseid:* - * (incr)
 * 2 : 收货id分配 string (key-value) : logisticsinid - * (incr)
 * 2 : 收货商品数量（入库统计用） string (key-value) : number:logisticsinid:* - * (incr)
 * 2 : 发货id分配 string (key-value) : logisticsoutid - * (incr)
 * 2 : 入库单id分配 string (key-value) : warehouseinid - * (incr)
 * 3 : 算法空间
 * 3 : 算法基础参数 : 迭代次数 string (key-value) : Iteration - number
 * 3 : 算法基础参数 : 环境容纳量 string (key-value) : environmentalCapacity - number (选择操作获得染色体数略大于环境容纳量)
 * 3 : 算法基础参数 : 交叉率 string (key-value) : Crossover - number
 * 3 : 算法基础参数 : 变异率 string (key-value) : Mutation - number
 * 3 : 可变用户参数 : 预算 string (key-value) : Budget - number
 * 3 : 原始基因库 set (key-member): *(category) - *(goodsid)
 * 3 : 原始基因信息(评分和限制性条件) hash (key-field-value) *(goodsid) - *(limit) - *(retail)
 * 3 : 每代个体分配序号 string (key-value) : index:generation:*- * (incr)
 * 3 : 个体基因信息 hash (key-field-value) generation:*:index:* - * (category) - * (goodsid)
 * 3 : 一代个体集合(含适应度) sorted_set (key-member-score) : generation:* - * (index) - * (score)
 * 3 : 被选择个体(进行交叉操作) list (key-value) : selection:generation:* - *(index)
 */
