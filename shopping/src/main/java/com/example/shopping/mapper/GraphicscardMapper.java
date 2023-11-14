package com.example.shopping.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.shopping.entity.Graphicscard;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author wu
 * 2022.3.28
 */
@Mapper
public interface GraphicscardMapper extends BaseMapper<Graphicscard> {
    @Update("UPDATE goods_graphics_card SET is_delete = 1 WHERE id = #{id}")
    int lodelete(Integer id);

}
