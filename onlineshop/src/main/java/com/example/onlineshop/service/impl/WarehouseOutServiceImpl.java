package com.example.onlineshop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.onlineshop.entity.WarehouseOut;
import com.example.onlineshop.mapper.WarehouseOutMapper;
import com.example.onlineshop.service.IWarehouseOutService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * service实现类
 * @author wu
 * 2022.5.20
 */
@Service
public class WarehouseOutServiceImpl extends ServiceImpl<WarehouseOutMapper, WarehouseOut> implements IWarehouseOutService {

    @Resource
    private WarehouseOutMapper warehouseOutMapper;

    /**
     * 列表查询，全部显示
     */
    @Override
    public List<WarehouseOut> queryWarehouseOutList() {
        return warehouseOutMapper.selectList(null);
    }

    /**
     * 分页查询
     */
    @Override
    public IPage<WarehouseOut> selectWarehouseOutPage(int pageNum, int pageSize, String param) {
        QueryWrapper<WarehouseOut> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("is_delete",0);
        if(StringUtils.isNotEmpty(param)){
            queryWrapper
                    .eq("source_id",param);
                    //ID准确查询查询

        }
        Page<WarehouseOut> page=new Page<>(pageNum,pageSize);
        return warehouseOutMapper.selectPage(page,queryWrapper);
    }

    @Override
    public int addWarehouseOut(WarehouseOut warehouseOut) {
        return warehouseOutMapper.insert(warehouseOut);
    }

    @Override
    public int editWarehouseOut(WarehouseOut warehouseOut) {
        return warehouseOutMapper.updateById(warehouseOut);
    }

    /**
     * 根据主键删除对象
     */
    @Override
    public int delWarehouseOutById(Integer id) {
        return warehouseOutMapper.deleteById(id);
    }

    /**
     * 根据主键查询对象
     */
    @Override
    public WarehouseOut queryWarehouseOutById(Integer id) {
        return warehouseOutMapper.selectById(id);
    }

    /**
     * 逻辑删除
     * @param id
     * @return 0
     */
    @Override
    public int lodelWarehouseOutById(Integer id){
        return warehouseOutMapper.lodelete(id);
    }

}

