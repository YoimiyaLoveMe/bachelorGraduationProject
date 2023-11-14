package com.example.shopping.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.shopping.entity.Customer;
import com.example.shopping.entity.OrderRetail;
import com.example.shopping.mapper.OrderRetailMapper;
import com.example.shopping.service.IOrderRetailService;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * service实现类
 * @author wu
 * 2022.4.10
 */
@Service
public class OrderRetailServiceImpl extends ServiceImpl<OrderRetailMapper, OrderRetail> implements IOrderRetailService {

    @Resource
    private OrderRetailMapper orderRetailMapper;

    /**
     * 列表查询，全部显示
     */
    @Override
    public List<OrderRetail> queryOrderRetailList() {
        return orderRetailMapper.selectList(null);
    }

    /**
     * 分页查询
     */
    @Override
    public IPage<OrderRetail> selectOrderRetailPage(int pageNum, int pageSize, String param) {
        Customer customer = (Customer) SecurityUtils.getSubject().getPrincipal();
        QueryWrapper<OrderRetail> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("is_delete",0);
        //当前用户过滤
        queryWrapper.eq("customer_id",customer.getId());
        if(StringUtils.isNotEmpty(param)){
            queryWrapper
                    //.eq("need_id",param)
                    //ID准确查询查询
                    .like("name",param);
                    //名称模糊查询

        }
        Page<OrderRetail> page=new Page<>(pageNum,pageSize);
        return orderRetailMapper.selectPage(page,queryWrapper);
    }

    @Override
    public int addOrderRetail(OrderRetail orderRetail) {
        return orderRetailMapper.insert(orderRetail);
    }

    @Override
    public int editOrderRetail(OrderRetail orderRetail) {
        return orderRetailMapper.updateById(orderRetail);
    }

    /**
     * 根据主键删除对象
     */
    @Override
    public int delOrderRetailById(Integer orderRetail_id) {
        return orderRetailMapper.deleteById(orderRetail_id);
    }

    /**
     * 根据主键查询对象
     */
    @Override
    public OrderRetail queryOrderRetailById(Integer orderRetail_id) {
        return orderRetailMapper.selectById(orderRetail_id);
    }

    /**
     * 逻辑删除
     * @param id
     * @return 0
     */
    @Override
    public int lodelOrderRetailById(Integer id){
        return orderRetailMapper.lodelete(id);
    }

    /**
     * 订单付款
     * @param id
     * @return 0
     */
    @Override
    public int payOrderRetailById(Integer id){
        return orderRetailMapper.pay(id);
    }

    /**
     * 取消订单
     * @param id
     * @return 0
     */
    @Override
    public int cancelOrderRetailById(Integer id){
        return orderRetailMapper.cancel(id);
    }
}

