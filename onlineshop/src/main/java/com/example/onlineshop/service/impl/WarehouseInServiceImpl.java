package com.example.onlineshop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.onlineshop.entity.WarehouseIn;
import com.example.onlineshop.mapper.WarehouseInMapper;
import com.example.onlineshop.service.IWarehouseInService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * service实现类
 * @author wu
 * 2022.4.23
 */
@Service
public class WarehouseInServiceImpl extends ServiceImpl<WarehouseInMapper, WarehouseIn> implements IWarehouseInService {

    @Resource
    private WarehouseInMapper warehouseInMapper;

    /**
     * 列表查询，全部显示
     */
    @Override
    public List<WarehouseIn> queryWarehouseInList() {
        return warehouseInMapper.selectList(null);
    }

    /**
     * 分页查询
     */
    @Override
    public IPage<WarehouseIn> selectWarehouseInPage(int pageNum, int pageSize, String param) {
        QueryWrapper<WarehouseIn> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("is_delete",0);
        if(StringUtils.isNotEmpty(param)){
            queryWrapper
                    .eq("source_id",param);
                    //ID准确查询查询

        }
        Page<WarehouseIn> page=new Page<>(pageNum,pageSize);
        return warehouseInMapper.selectPage(page,queryWrapper);
    }

    @Override
    public int addWarehouseIn(WarehouseIn warehouseIn) {
        return warehouseInMapper.insert(warehouseIn);
    }

    @Override
    public int editWarehouseIn(WarehouseIn warehouseIn) {
        return warehouseInMapper.updateById(warehouseIn);
    }

    /**
     * 根据主键删除对象
     */
    @Override
    public int delWarehouseInById(Integer id) {
        return warehouseInMapper.deleteById(id);
    }

    /**
     * 根据主键查询对象
     */
    @Override
    public WarehouseIn queryWarehouseInById(Integer id) {
        return warehouseInMapper.selectById(id);
    }

    /**
     * 逻辑删除
     * @param id
     * @return 0
     */
    @Override
    public int lodelWarehouseInById(Integer id){
        return warehouseInMapper.lodelete(id);
    }

}

