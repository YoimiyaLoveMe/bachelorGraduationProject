package com.example.onlineshop.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.onlineshop.entity.Heatsink;

import java.util.List;


/**
 * @author wu
 * 2022.3.28
 */
public interface IHeatsinkService extends IService<Heatsink> {
    public IPage<Heatsink> selectHeatsinkPage(int pageNum, int pageSize, String param);
    public int addHeatsink(Heatsink heatsink);
    public int editHeatsink(Heatsink heatsink);
    public int delHeatsinkById(Integer heatsink_id);
    public List<Heatsink> queryHeatsinkList();
    public Heatsink queryHeatsinkById(Integer heatsink_id);

    public int lodelHeatsinkById(Integer id);

    }


