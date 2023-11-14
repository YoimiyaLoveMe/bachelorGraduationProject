package com.example.onlineshop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.onlineshop.entity.Warehouse;
import com.example.onlineshop.mapper.WarehouseMapper;
import com.example.onlineshop.service.IWarehouseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * service实现类
 * @author wu
 * 2022.4.20
 */
@Service
public class WarehouseServiceImpl extends ServiceImpl<WarehouseMapper, Warehouse> implements IWarehouseService {

    @Resource
    private WarehouseMapper warehouseMapper;

    /**
     * 列表查询，全部显示
     */
    @Override
    public List<Warehouse> queryWarehouseList() {
        return warehouseMapper.selectList(null);
    }

    /**
     * 分页查询
     */
    @Override
    public IPage<Warehouse> selectWarehousePage(int pageNum, int pageSize, String param) {
        QueryWrapper<Warehouse> queryWrapper=new QueryWrapper<>();
        if(StringUtils.isNotEmpty(param)){
            queryWrapper
                    .eq("need_id",param);
//                    ID准确查询查询
//                    .like("goods_name",param);
//                    //名称模糊查询

        }
        Page<Warehouse> page=new Page<>(pageNum,pageSize);
        return warehouseMapper.selectPage(page,queryWrapper);
    }

    @Override
    public int addWarehouse(Warehouse warehouse) {
        return warehouseMapper.insert(warehouse);
    }

    @Override
    public int editWarehouse(Warehouse warehouse) {
        return warehouseMapper.updateById(warehouse);
    }

    /**
     * 根据主键删除对象
     */
    @Override
    public int delWarehouseById(Integer warehouse_id) {
        return warehouseMapper.deleteById(warehouse_id);
    }

    /**
     * 根据主键查询对象
     */
    @Override
    public Warehouse queryWarehouseById(Integer warehouse_id) {
        return warehouseMapper.selectById(warehouse_id);
    }
}

