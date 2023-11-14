package com.example.onlineshop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.onlineshop.entity.Case;
import com.example.onlineshop.entity.Cpu;
import com.example.onlineshop.entity.Worker;
import com.example.onlineshop.mapper.WorkerMapper;
import com.example.onlineshop.service.IWorkerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户service实现类
 * @author wu
 * 2022.3
 */

@Service
public class WorkerServiceImpl extends ServiceImpl<WorkerMapper,Worker> implements IWorkerService{

    @Resource
    private WorkerMapper workerMapper;
    /**
     * 根据账号查询对象(登录)
     */
    @Override
    public Worker queryWorkerByWorkerId(Worker worker) {
        QueryWrapper<Worker> wrapper = new QueryWrapper<>();
        wrapper.eq("id",worker.getId()).eq("is_delete",0);
        return workerMapper.selectOne(wrapper);
    }

    /**
     * 根据主键查询对象(管理)
     */
    @Override
    public Worker queryWorkerById(String id) {
        return workerMapper.selectById(id);
    }


    /**
     * 注册，新增用户
     */
    @Override
    public int addWorker(Worker worker) {
        return workerMapper.insert(worker);
    }

    /**
     * 列表查询，全部显示
     */
    @Override
    public List<Worker> queryWorkerList() {
        return workerMapper.selectList(null);
    }

    /**
     * 分页查询
     */
    @Override
    public IPage<Worker> selectWorkerPage(int pageNum, int pageSize, String param) {
        QueryWrapper<Worker> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("is_delete",0);
        if(StringUtils.isNotEmpty(param)){
            queryWrapper
                    //.eq("need_id",param)
                    //ID准确查询查询
                    .like("name",param);
                    //名称模糊查询

        }
        Page<Worker> page=new Page<>(pageNum,pageSize);
        return workerMapper.selectPage(page,queryWrapper);
    }

    /**
     * 编辑用户信息
     */
    @Override
    public  int editWorker(Worker worker) { return  workerMapper.updateById(worker); }

    /**
     * 逻辑删除
     * @param id
     * @return 0
     */
    @Override
    public int lodelWorkerById(String id){
        return workerMapper.lodelete(id);
    }

    /**
     * 打卡
     * @param id
     * @return 0
     */
    @Override
    public int clockin(String id){
        return workerMapper.clockin(id);
    }

    @Override
    public int clockout(String id){
        return workerMapper.clockout(id);
    }
}
