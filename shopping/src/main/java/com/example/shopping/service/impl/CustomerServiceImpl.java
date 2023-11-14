package com.example.shopping.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.shopping.entity.Customer;
import com.example.shopping.mapper.CustomerMapper;
import com.example.shopping.service.ICustomerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户service实现类
 * @author wu
 * 2022.4
 */

@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements ICustomerService {

    @Resource
    private CustomerMapper customerMapper;
    /**
     * 根据用户名查询对象
     */
    @Override
    public Customer queryCustomerByCustomerId(Customer customer) {
        QueryWrapper<Customer> wrapper = new QueryWrapper<>();
        wrapper.eq("id", customer.getId());
        return customerMapper.selectOne(wrapper);
    }

    /**
     * 注册，新增用户
     */
    @Override
    public int addCustomer(Customer customer) {
        return customerMapper.insert(customer);
    }

    /**
     * 编辑用户信息
     */
    @Override
    public  int editCustomer(Customer customer) { return  customerMapper.updateById(customer); }


}
