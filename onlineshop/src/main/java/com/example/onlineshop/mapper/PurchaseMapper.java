package com.example.onlineshop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.onlineshop.entity.Purchase;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * @author wu
 * 2022.4.20
 */
@Mapper
public interface PurchaseMapper extends BaseMapper<Purchase> {
    @Update("UPDATE purchase_order SET state = '已撤销' WHERE id = #{id}")
    int lodelete(Integer id);

    @Update("UPDATE purchase_order SET state = '已打回' WHERE id = #{id}")
    int refuse(Integer id);

    @Update("UPDATE purchase_order SET state = '已通过' WHERE id = #{id}")
    int examine(Integer id);

    @Update("UPDATE purchase_order SET state = '已完成' WHERE id = #{id}")
    int finish(Integer id);
}
