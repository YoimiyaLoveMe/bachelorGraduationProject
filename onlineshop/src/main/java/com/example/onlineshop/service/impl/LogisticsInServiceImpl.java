package com.example.onlineshop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.onlineshop.entity.LogisticsIn;
import com.example.onlineshop.mapper.LogisticsInMapper;
import com.example.onlineshop.service.ILogisticsInService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * service实现类
 * @author wu
 * 2022.4.21
 */
@Service
public class LogisticsInServiceImpl extends ServiceImpl<LogisticsInMapper, LogisticsIn> implements ILogisticsInService {

    @Resource
    private LogisticsInMapper logisticsInMapper;

    /**
     * 列表查询，全部显示
     */
    @Override
    public List<LogisticsIn> queryLogisticsInList() {
        return logisticsInMapper.selectList(null);
    }

    /**
     * 分页查询
     */
    @Override
    public IPage<LogisticsIn> selectLogisticsInPage(int pageNum, int pageSize, String param) {
        QueryWrapper<LogisticsIn> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("is_delete",0);
        if(StringUtils.isNotEmpty(param)){
            queryWrapper
                    .eq("source_id",param);
                    //ID准确查询查询

        }
        Page<LogisticsIn> page=new Page<>(pageNum,pageSize);
        return logisticsInMapper.selectPage(page,queryWrapper);
    }

    @Override
    public int addLogisticsIn(LogisticsIn logisticsIn) {
        return logisticsInMapper.insert(logisticsIn);
    }

    @Override
    public int editLogisticsIn(LogisticsIn logisticsIn) {
        return logisticsInMapper.updateById(logisticsIn);
    }

    /**
     * 根据主键删除对象
     */
    @Override
    public int delLogisticsInById(Integer id) {
        return logisticsInMapper.deleteById(id);
    }

    /**
     * 根据主键查询对象
     */
    @Override
    public LogisticsIn queryLogisticsInById(Integer id) {
        return logisticsInMapper.selectById(id);
    }

    /**
     * 逻辑删除
     * @param id
     * @return 0
     */
    @Override
    public int lodelLogisticsInById(Integer id){
        return logisticsInMapper.lodelete(id);
    }

}

