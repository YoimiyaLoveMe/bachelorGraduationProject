package com.example.onlineshop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.onlineshop.entity.Cpu;
import com.example.onlineshop.mapper.CpuMapper;
import com.example.onlineshop.service.ICpuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * service实现类
 * @author wu
 * 2022.3.28
 */
@Service
public class CpuServiceImpl extends ServiceImpl<CpuMapper, Cpu> implements ICpuService {

    @Resource
    private CpuMapper cpuMapper;

    /**
     * 列表查询，全部显示
     */
    @Override
    public List<Cpu> queryCpuList() {
        return cpuMapper.selectList(null);
    }

    /**
     * 分页查询
     */
    @Override
    public IPage<Cpu> selectCpuPage(int pageNum, int pageSize, String param) {
        QueryWrapper<Cpu> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("is_delete",0);
        if(StringUtils.isNotEmpty(param)){
            queryWrapper
                    //.eq("need_id",param)
                    //ID准确查询查询
                    .like("name",param);
                    //名称模糊查询

        }
        Page<Cpu> page=new Page<>(pageNum,pageSize);
        return cpuMapper.selectPage(page,queryWrapper);
    }

    @Override
    public int addCpu(Cpu cpu) {
        return cpuMapper.insert(cpu);
    }

    @Override
    public int editCpu(Cpu cpu) {
        return cpuMapper.updateById(cpu);
    }

    /**
     * 根据主键删除对象
     */
    @Override
    public int delCpuById(Integer cpu_id) {
        return cpuMapper.deleteById(cpu_id);
    }

    /**
     * 根据主键查询对象
     */
    @Override
    public Cpu queryCpuById(Integer cpu_id) {
        return cpuMapper.selectById(cpu_id);
    }

    /**
     * 逻辑删除
     * @param id
     * @return 0
     */
    @Override
    public int lodelCpuById(Integer id){
        return cpuMapper.lodelete(id);
    }

}

