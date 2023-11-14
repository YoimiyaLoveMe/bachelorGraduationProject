package com.example.onlineshop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.onlineshop.entity.Worker;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author wu
 * 2022.3
 */
@Mapper
public interface WorkerMapper extends BaseMapper<Worker> {

    @Update("UPDATE worker SET working = 1 WHERE id = #{id}")
    int clockin(String id);

    @Update("UPDATE worker SET working = 0 WHERE id = #{id}")
    int clockout(String id);

    @Update("UPDATE worker SET is_delete = 1 WHERE id = #{id}")
    int lodelete(String id);
}
