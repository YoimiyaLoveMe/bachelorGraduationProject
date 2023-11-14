package com.example.shopping.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.shopping.entity.Power;
import com.example.shopping.mapper.PowerMapper;
import com.example.shopping.service.IPowerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * service实现类
 * @author wu
 * 2022.3.28
 */
@Service
public class PowerServiceImpl extends ServiceImpl<PowerMapper, Power> implements IPowerService {

    @Resource
    private PowerMapper powerMapper;

    /**
     * 列表查询，全部显示
     */
    @Override
    public List<Power> queryPowerList() {
        return powerMapper.selectList(null);
    }

    /**
     * 分页查询
     */
    @Override
    public IPage<Power> selectPowerPage(int pageNum, int pageSize, String param) {
        QueryWrapper<Power> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("is_delete",0);
        if(StringUtils.isNotEmpty(param)){
            queryWrapper
                    //.eq("need_id",param)
                    //ID准确查询查询
                    .like("name",param);
                    //名称模糊查询

        }
        Page<Power> page=new Page<>(pageNum,pageSize);
        return powerMapper.selectPage(page,queryWrapper);
    }

    @Override
    public int addPower(Power power) {
        return powerMapper.insert(power);
    }

    @Override
    public int editPower(Power power) {
        return powerMapper.updateById(power);
    }

    /**
     * 根据主键删除对象
     */
    @Override
    public int delPowerById(Integer power_id) {
        return powerMapper.deleteById(power_id);
    }

    /**
     * 根据主键查询对象
     */
    @Override
    public Power queryPowerById(Integer power_id) {
        return powerMapper.selectById(power_id);
    }

    /**
     * 逻辑删除
     * @param id
     * @return 0
     */
    @Override
    public int lodelPowerById(Integer id){
        return powerMapper.lodelete(id);
    }

}

