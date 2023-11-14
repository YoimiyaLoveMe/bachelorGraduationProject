package com.example.shopping.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.shopping.entity.Customer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * @author wu
 * 2022.4
 */
@Mapper
public interface CustomerMapper extends BaseMapper<Customer> {

}
