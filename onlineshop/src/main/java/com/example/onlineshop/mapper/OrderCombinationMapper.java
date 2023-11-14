package com.example.onlineshop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.onlineshop.entity.OrderCombination;
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

}
