package com.example.onlineshop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.onlineshop.entity.LogisticsOut;
import com.example.onlineshop.mapper.LogisticsOutMapper;
import com.example.onlineshop.service.ILogisticsOutService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * service实现类
 * @author wu
 * 2022.5.21
 */
@Service
public class LogisticsOutServiceImpl extends ServiceImpl<LogisticsOutMapper, LogisticsOut> implements ILogisticsOutService {

    @Resource
    private LogisticsOutMapper logisticsOutMapper;

    /**
     * 列表查询，全部显示
     */
    @Override
    public List<LogisticsOut> queryLogisticsOutList() {
        return logisticsOutMapper.selectList(null);
    }

    /**
     * 分页查询
     */
    @Override
    public IPage<LogisticsOut> selectLogisticsOutPage(int pageNum, int pageSize, String param) {
        QueryWrapper<LogisticsOut> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("is_delete",0);
        if(StringUtils.isNotEmpty(param)){
            queryWrapper
                    .eq("source_id",param);
                    //ID准确查询查询

        }
        Page<LogisticsOut> page=new Page<>(pageNum,pageSize);
        return logisticsOutMapper.selectPage(page,queryWrapper);
    }

    @Override
    public int addLogisticsOut(LogisticsOut logisticsOut) {
        return logisticsOutMapper.insert(logisticsOut);
    }

    @Override
    public int editLogisticsOut(LogisticsOut logisticsOut) {
        return logisticsOutMapper.updateById(logisticsOut);
    }

    /**
     * 根据主键删除对象
     */
    @Override
    public int delLogisticsOutById(Integer id) {
        return logisticsOutMapper.deleteById(id);
    }

    /**
     * 根据主键查询对象
     */
    @Override
    public LogisticsOut queryLogisticsOutById(Integer id) {
        return logisticsOutMapper.selectById(id);
    }

    /**
     * 逻辑删除
     * @param id
     * @return 0
     */
    @Override
    public int lodelLogisticsOutById(Integer id){
        return logisticsOutMapper.lodelete(id);
    }

}

