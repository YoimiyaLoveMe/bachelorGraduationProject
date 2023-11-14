package com.example.onlineshop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.onlineshop.entity.Supply;
import com.example.onlineshop.mapper.SupplyMapper;
import com.example.onlineshop.service.ISupplyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * service实现类
 * @author wu
 * 2022.4.17
 */
@Service
public class SupplyServiceImpl extends ServiceImpl<SupplyMapper, Supply> implements ISupplyService {

    @Resource
    private SupplyMapper supplyMapper;

    /**
     * 列表查询，全部显示
     */
    @Override
    public List<Supply> querySupplyList() {
        return supplyMapper.selectList(null);
    }

    /**
     * 分页查询
     */
    @Override
    public IPage<Supply> selectSupplyPage(int pageNum, int pageSize, String param) {
        QueryWrapper<Supply> queryWrapper=new QueryWrapper<>();
        if(StringUtils.isNotEmpty(param)){
            queryWrapper
                    //.eq("need_id",param)
                    //ID准确查询查询
                    .like("goods_name",param);
                    //名称模糊查询

        }
        Page<Supply> page=new Page<>(pageNum,pageSize);
        return supplyMapper.selectPage(page,queryWrapper);
    }

    @Override
    public int addSupply(Supply supply) {
        return supplyMapper.insert(supply);
    }

    @Override
    public int editSupply(Supply supply) {
        return supplyMapper.updateById(supply);
    }

    /**
     * 根据主键删除对象
     */
    @Override
    public int delSupplyById(Integer supply_id) {
        return supplyMapper.deleteById(supply_id);
    }

    /**
     * 根据主键查询对象
     */
    @Override
    public Supply querySupplyById(Integer supply_id) {
        return supplyMapper.selectById(supply_id);
    }
}

