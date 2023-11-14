package com.example.onlineshop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.onlineshop.entity.WorkerJob;
import com.example.onlineshop.mapper.WorkerJobMapper;
import com.example.onlineshop.service.IWorkerJobService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * service实现类
 * @author wu
 * 2022.4.13
 */
@Service
public class WorkerJobServiceImpl extends ServiceImpl<WorkerJobMapper, WorkerJob> implements IWorkerJobService {

    @Resource
    private WorkerJobMapper workerjobMapper;

    /**
     * 列表查询，全部显示
     */
    @Override
    public List<WorkerJob> queryWorkerJobList() {
        return workerjobMapper.selectList(null);
    }

    /**
     * 分页查询
     */
    @Override
    public IPage<WorkerJob> selectWorkerJobPage(int pageNum, int pageSize, String param) {
        QueryWrapper<WorkerJob> queryWrapper=new QueryWrapper<>();
        if(StringUtils.isNotEmpty(param)){
            queryWrapper
                    .like("name",param);
                    //名称模糊查询

        }
        Page<WorkerJob> page=new Page<>(pageNum,pageSize);
        return workerjobMapper.selectPage(page,queryWrapper);
    }


    @Override
    public int addWorkerJob(WorkerJob workerjob) {
        return workerjobMapper.insert(workerjob);
    }

    @Override
    public int editWorkerJob(WorkerJob workerjob) {
        return workerjobMapper.updateById(workerjob);
    }

    /**
     * 根据主键删除对象
     */
    @Override
    public int delWorkerJobById(Integer job_id) {
        return workerjobMapper.deleteById(job_id);
    }

    /**
     * 根据主键查询对象
     */
    @Override
    public WorkerJob queryWorkerJobById(Integer job_id) {
        return workerjobMapper.selectById(job_id);
    }
    
}

