package com.example.shopping.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.shopping.entity.Heatsink;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author wu
 * 2022.3.28
 */
@Mapper
public interface HeatsinkMapper extends BaseMapper<Heatsink> {
    @Update("UPDATE goods_heatsink SET is_delete = 1 WHERE id = #{id}")
    int lodelete(Integer id);

}
