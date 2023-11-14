package com.example.onlineshop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.onlineshop.entity.LogisticsOut;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * @author wu
 * 2022.5.21
 */
@Mapper
public interface LogisticsOutMapper extends BaseMapper<LogisticsOut> {

    @Update("UPDATE logistics_out SET is_delete = 1 WHERE id = #{id}")
    int lodelete(Integer id);
}
