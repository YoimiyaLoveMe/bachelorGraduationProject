package com.example.onlineshop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.onlineshop.entity.WarehouseIn;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * @author wu
 * 2022.4.23
 */
@Mapper
public interface WarehouseInMapper extends BaseMapper<WarehouseIn> {

    @Update("UPDATE warehouse_in SET is_delete = 1 WHERE id = #{id}")
    int lodelete(Integer id);
}
