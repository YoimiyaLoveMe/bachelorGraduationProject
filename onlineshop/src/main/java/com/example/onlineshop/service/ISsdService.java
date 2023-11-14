package com.example.onlineshop.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.onlineshop.entity.Ssd;

import java.util.List;


/**
 * @author wu
 * 2022.4.5
 */
public interface ISsdService extends IService<Ssd> {
    public IPage<Ssd> selectSsdPage(int pageNum, int pageSize, String param);
    public int addSsd(Ssd ssd);
    public int editSsd(Ssd ssd);
    public int delSsdById(Integer ssd_id);
    public List<Ssd> querySsdList();
    public Ssd querySsdById(Integer ssd_id);

    public int lodelSsdById(Integer id);

    }


