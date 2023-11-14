package com.example.onlineshop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.onlineshop.entity.Motherboard;
import com.example.onlineshop.mapper.MotherboardMapper;
import com.example.onlineshop.service.IMotherboardService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * service实现类
 * @author wu
 * 2022.3.27
 */
@Service
public class MotherboardServiceImpl extends ServiceImpl<MotherboardMapper, Motherboard> implements IMotherboardService {

    @Resource
    private MotherboardMapper motherboardMapper;

    /**
     * 列表查询，全部显示
     */
    @Override
    public List<Motherboard> queryMotherboardList() {
        return motherboardMapper.selectList(null);
    }

    /**
     * 分页查询
     */
    @Override
    public IPage<Motherboard> selectMotherboardPage(int pageNum, int pageSize, String param) {
        QueryWrapper<Motherboard> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_delete",0);
        if(StringUtils.isNotEmpty(param)){
            queryWrapper
                    //.eq("need_id",param)
                    //ID准确查询查询
                    .like("name",param);
                    //名称模糊查询

        }
        Page<Motherboard> page = new Page<>(pageNum,pageSize);
        return motherboardMapper.selectPage(page,queryWrapper);
    }

    @Override
    public int addMotherboard(Motherboard motherboard) {
        return motherboardMapper.insert(motherboard);
    }

    @Override
    public int editMotherboard(Motherboard motherboard) {
        return motherboardMapper.updateById(motherboard);
    }

    /**
     * 根据主键删除对象
     */
    @Override
    public int delMotherboardById(Integer board_id) {
        return motherboardMapper.deleteById(board_id);
    }

    /**
     * 根据主键查询对象
     */
    @Override
    public Motherboard queryMotherboardById(Integer board_id) {
        return motherboardMapper.selectById(board_id);
    }


    /**
     * 逻辑删除
     */
    @Override
    public int lodelMotherboardById(Integer id){
        return motherboardMapper.lodelete(id);
    }

}

