package com.example.onlineshop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.onlineshop.entity.Cpu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author wu
 * 2022.3.28
 */
@Mapper
public interface CpuMapper extends BaseMapper<Cpu> {
    @Update("UPDATE goods_cpu SET is_delete = 1 WHERE id = #{id}")
    int lodelete(Integer id);

}
