package com.example.onlineshop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.onlineshop.entity.LogisticsIn;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * @author wu
 * 2022.4.22
 */
@Mapper
public interface LogisticsInMapper extends BaseMapper<LogisticsIn> {

    @Update("UPDATE logistics_in SET is_delete = 1 WHERE id = #{id}")
    int lodelete(Integer id);
}
