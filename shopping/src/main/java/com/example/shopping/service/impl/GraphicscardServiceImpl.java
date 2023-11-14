package com.example.shopping.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.shopping.entity.Graphicscard;
import com.example.shopping.mapper.GraphicscardMapper;
import com.example.shopping.service.IGraphicscardService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * service实现类
 * @author wu
 * 2022.3.28
 */
@Service
public class GraphicscardServiceImpl extends ServiceImpl<GraphicscardMapper, Graphicscard> implements IGraphicscardService {

    @Resource
    private GraphicscardMapper graphicscardMapper;

    /**
     * 列表查询，全部显示
     */
    @Override
    public List<Graphicscard> queryGraphicscardList() {
        return graphicscardMapper.selectList(null);
    }

    /**
     * 分页查询
     */
    @Override
    public IPage<Graphicscard> selectGraphicscardPage(int pageNum, int pageSize, String param) {
        QueryWrapper<Graphicscard> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("is_delete",0);
        if(StringUtils.isNotEmpty(param)){
            queryWrapper
                    //.eq("need_id",param)
                    //ID准确查询查询
                    .like("name",param);
                    //名称模糊查询

        }
        Page<Graphicscard> page=new Page<>(pageNum,pageSize);
        return graphicscardMapper.selectPage(page,queryWrapper);
    }

    @Override
    public int addGraphicscard(Graphicscard graphicscard) {
        return graphicscardMapper.insert(graphicscard);
    }

    @Override
    public int editGraphicscard(Graphicscard graphicscard) {
        return graphicscardMapper.updateById(graphicscard);
    }

    /**
     * 根据主键删除对象
     */
    @Override
    public int delGraphicscardById(Integer graphicscard_id) {
        return graphicscardMapper.deleteById(graphicscard_id);
    }

    /**
     * 根据主键查询对象
     */
    @Override
    public Graphicscard queryGraphicscardById(Integer graphicscard_id) {
        return graphicscardMapper.selectById(graphicscard_id);
    }

    /**
     * 逻辑删除
     * @param id
     * @return 0
     */
    @Override
    public int lodelGraphicscardById(Integer id){
        return graphicscardMapper.lodelete(id);
    }

}

