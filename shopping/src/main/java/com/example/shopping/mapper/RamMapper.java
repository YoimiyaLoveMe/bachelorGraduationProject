package com.example.shopping.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.shopping.entity.Ram;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author wu
 * 2022.4.4
 */
@Mapper
public interface RamMapper extends BaseMapper<Ram> {
    @Update("UPDATE goods_ram SET is_delete = 1 WHERE id = #{id}")
    int lodelete(Integer id);
}
