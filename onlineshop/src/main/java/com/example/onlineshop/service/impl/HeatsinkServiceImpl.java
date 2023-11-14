package com.example.onlineshop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.onlineshop.entity.Heatsink;
import com.example.onlineshop.mapper.HeatsinkMapper;
import com.example.onlineshop.service.IHeatsinkService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * service实现类
 * @author wu
 * 2022.3.28
 */
@Service
public class HeatsinkServiceImpl extends ServiceImpl<HeatsinkMapper, Heatsink> implements IHeatsinkService {

    @Resource
    private HeatsinkMapper heatsinkMapper;

    /**
     * 列表查询，全部显示
     */
    @Override
    public List<Heatsink> queryHeatsinkList() {
        return heatsinkMapper.selectList(null);
    }

    /**
     * 分页查询
     */
    @Override
    public IPage<Heatsink> selectHeatsinkPage(int pageNum, int pageSize, String param) {
        QueryWrapper<Heatsink> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("is_delete",0);
        if(StringUtils.isNotEmpty(param)){
            queryWrapper
                    //.eq("need_id",param)
                    //ID准确查询查询
                    .like("name",param);
                    //名称模糊查询

        }
        Page<Heatsink> page=new Page<>(pageNum,pageSize);
        return heatsinkMapper.selectPage(page,queryWrapper);
    }

    @Override
    public int addHeatsink(Heatsink heatsink) {
        return heatsinkMapper.insert(heatsink);
    }

    @Override
    public int editHeatsink(Heatsink heatsink) {
        return heatsinkMapper.updateById(heatsink);
    }

    /**
     * 根据主键删除对象
     */
    @Override
    public int delHeatsinkById(Integer heatsink_id) {
        return heatsinkMapper.deleteById(heatsink_id);
    }

    /**
     * 根据主键查询对象
     */
    @Override
    public Heatsink queryHeatsinkById(Integer heatsink_id) {
        return heatsinkMapper.selectById(heatsink_id);
    }

    /**
     * 逻辑删除
     * @param id
     * @return 0
     */
    @Override
    public int lodelHeatsinkById(Integer id){
        return heatsinkMapper.lodelete(id);
    }

}

