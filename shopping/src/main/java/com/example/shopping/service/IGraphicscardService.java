package com.example.shopping.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.shopping.entity.Graphicscard;

import java.util.List;


/**
 * @author wu
 * 2022.3.28
 */
public interface IGraphicscardService extends IService<Graphicscard> {
    public IPage<Graphicscard> selectGraphicscardPage(int pageNum, int pageSize, String param);
    public int addGraphicscard(Graphicscard graphicscard);
    public int editGraphicscard(Graphicscard graphicscard);
    public int delGraphicscardById(Integer graphicscard_id);
    public List<Graphicscard> queryGraphicscardList();
    public Graphicscard queryGraphicscardById(Integer graphicscard_id);

    public int lodelGraphicscardById(Integer id);

    }


