package com.example.onlineshop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.onlineshop.entity.Ssd;
import com.example.onlineshop.mapper.SsdMapper;
import com.example.onlineshop.service.ISsdService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * service实现类
 * @author wu
 * 2022.4.5
 */
@Service
public class SsdServiceImpl extends ServiceImpl<SsdMapper, Ssd> implements ISsdService {

    @Resource
    private SsdMapper ssdMapper;

    /**
     * 列表查询，全部显示
     */
    @Override
    public List<Ssd> querySsdList() {
        return ssdMapper.selectList(null);
    }

    /**
     * 分页查询
     */
    @Override
    public IPage<Ssd> selectSsdPage(int pageNum, int pageSize, String passd) {
        QueryWrapper<Ssd> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("is_delete",0);
        if(StringUtils.isNotEmpty(passd)){
            queryWrapper
                    //.eq("need_id",passd)
                    //ID准确查询查询
                    .like("name",passd);
                    //名称模糊查询

        }
        Page<Ssd> page=new Page<>(pageNum,pageSize);
        return ssdMapper.selectPage(page,queryWrapper);
    }

    @Override
    public int addSsd(Ssd ssd) {
        return ssdMapper.insert(ssd);
    }

    @Override
    public int editSsd(Ssd ssd) {
        return ssdMapper.updateById(ssd);
    }

    /**
     * 根据主键删除对象
     */
    @Override
    public int delSsdById(Integer ssd_id) {
        return ssdMapper.deleteById(ssd_id);
    }

    /**
     * 根据主键查询对象
     */
    @Override
    public Ssd querySsdById(Integer ssd_id) {
        return ssdMapper.selectById(ssd_id);
    }

    /**
     * 逻辑删除
     * @passd id
     * @return 0
     */
    @Override
    public int lodelSsdById(Integer id){
        return ssdMapper.lodelete(id);
    }

}

