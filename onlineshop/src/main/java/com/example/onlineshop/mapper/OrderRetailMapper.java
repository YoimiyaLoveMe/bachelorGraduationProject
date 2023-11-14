package com.example.onlineshop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.onlineshop.entity.OrderRetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;


/**
 * @author wu
 * 2022.4.10
 */
@Mapper
public interface OrderRetailMapper extends BaseMapper<OrderRetail> {
    @Update("UPDATE order_retail SET is_delete = 1 WHERE id = #{id}")
    int lodelete(Integer id);

    @Update("UPDATE order_retail SET state = '已付款' WHERE id = #{id}")
    int pay(Integer id);

}
