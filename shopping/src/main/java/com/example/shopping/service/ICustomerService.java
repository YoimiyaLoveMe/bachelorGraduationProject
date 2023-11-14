package com.example.shopping.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.shopping.entity.Customer;

/**
 * 用户表的service接口
 * @author wu
 * 2022.4
 */
public interface ICustomerService extends IService<Customer> {

    Customer queryCustomerByCustomerId(Customer customer);
    public int addCustomer(Customer customer);
    public int editCustomer(Customer customer);

}
