package com.example.onlineshop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.onlineshop.entity.Ram;
import com.example.onlineshop.mapper.RamMapper;
import com.example.onlineshop.service.IRamService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * service实现类
 * @author wu
 * 2022.4.4
 */
@Service
public class RamServiceImpl extends ServiceImpl<RamMapper, Ram> implements IRamService {

    @Resource
    private RamMapper ramMapper;

    /**
     * 列表查询，全部显示
     */
    @Override
    public List<Ram> queryRamList() {
        return ramMapper.selectList(null);
    }

    /**
     * 分页查询
     */
    @Override
    public IPage<Ram> selectRamPage(int pageNum, int pageSize, String param) {
        QueryWrapper<Ram> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("is_delete",0);
        if(StringUtils.isNotEmpty(param)){
            queryWrapper
                    //.eq("need_id",param)
                    //ID准确查询查询
                    .like("name",param);
                    //名称模糊查询

        }
        Page<Ram> page=new Page<>(pageNum,pageSize);
        return ramMapper.selectPage(page,queryWrapper);
    }

    @Override
    public int addRam(Ram ram) {
        return ramMapper.insert(ram);
    }

    @Override
    public int editRam(Ram ram) {
        return ramMapper.updateById(ram);
    }

    /**
     * 根据主键删除对象
     */
    @Override
    public int delRamById(Integer ram_id) {
        return ramMapper.deleteById(ram_id);
    }

    /**
     * 根据主键查询对象
     */
    @Override
    public Ram queryRamById(Integer ram_id) {
        return ramMapper.selectById(ram_id);
    }

    /**
     * 逻辑删除
     * @param id
     * @return 0
     */
    @Override
    public int lodelRamById(Integer id){
        return ramMapper.lodelete(id);
    }

}

