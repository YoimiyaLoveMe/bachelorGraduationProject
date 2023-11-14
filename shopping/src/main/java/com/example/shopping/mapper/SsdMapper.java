package com.example.shopping.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.shopping.entity.Ssd;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author wu
 * 2022.4.5
 */
@Mapper
public interface SsdMapper extends BaseMapper<Ssd> {
    @Update("UPDATE goods_ssd SET is_delete = 1 WHERE id = #{id}")
    int lodelete(Integer id);

}
