package com.example.shopping.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.shopping.entity.OrderCombination;
import com.example.shopping.entity.OrderRetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;


/**
 * @author wu
 * 2022.4.18
 */
@Mapper
public interface OrderCombinationMapper extends BaseMapper<OrderCombination> {
    @Update("UPDATE order_combination SET is_delete = 1 WHERE id = #{id}")
    int lodelete(Integer id);

    @Update("UPDATE order_combination SET state = '已付款' WHERE id = #{id}")
    int pay(Integer id);

    @Update("UPDATE order_retail SET state = '已取消' WHERE id = #{id}")
    int cancel(Integer id);

}
