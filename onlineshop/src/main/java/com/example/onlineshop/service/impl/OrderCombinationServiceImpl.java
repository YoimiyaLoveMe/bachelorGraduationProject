package com.example.onlineshop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.onlineshop.entity.OrderCombination;
import com.example.onlineshop.mapper.OrderCombinationMapper;
import com.example.onlineshop.service.IOrderCombinationService;
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
public class OrderCombinationServiceImpl extends ServiceImpl<OrderCombinationMapper, OrderCombination> implements IOrderCombinationService {

    @Resource
    private OrderCombinationMapper orderCombinationMapper;

    /**
     * 列表查询，全部显示
     */
    @Override
    public List<OrderCombination> queryOrderCombinationList() {
        return orderCombinationMapper.selectList(null);
    }

    /**
     * 分页查询
     */
    @Override
    public IPage<OrderCombination> selectOrderCombinationPage(int pageNum, int pageSize, String param) {
        QueryWrapper<OrderCombination> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("is_delete",0);
        if(StringUtils.isNotEmpty(param)){
            queryWrapper
                    //.eq("need_id",param)
                    //ID准确查询查询
                    .like("name",param);
                    //名称模糊查询

        }
        Page<OrderCombination> page=new Page<>(pageNum,pageSize);
        return orderCombinationMapper.selectPage(page,queryWrapper);
    }

    @Override
    public int addOrderCombination(OrderCombination orderCombination) {
        return orderCombinationMapper.insert(orderCombination);
    }

    @Override
    public int editOrderCombination(OrderCombination orderCombination) {
        return orderCombinationMapper.updateById(orderCombination);
    }

    /**
     * 根据主键删除对象
     */
    @Override
    public int delOrderCombinationById(Integer orderCombination_id) {
        return orderCombinationMapper.deleteById(orderCombination_id);
    }

    /**
     * 根据主键查询对象
     */
    @Override
    public OrderCombination queryOrderCombinationById(Integer orderCombination_id) {
        return orderCombinationMapper.selectById(orderCombination_id);
    }

    /**
     * 逻辑删除
     * @param id
     * @return 0
     */
    @Override
    public int lodelOrderCombinationById(Integer id){
        return orderCombinationMapper.lodelete(id);
    }

    /**
     * 订单付款
     * @param id
     * @return 0
     */
    @Override
    public int payOrderCombinationById(Integer id){
        return orderCombinationMapper.pay(id);
    }

}

