package com.example.shopping.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.shopping.entity.Case;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author wu
 * 2022.3.28
 */
@Mapper
public interface CaseMapper extends BaseMapper<Case> {
    @Update("UPDATE goods_case SET is_delete = 1 WHERE id = #{id}")
    int lodelete(Integer id);

}
