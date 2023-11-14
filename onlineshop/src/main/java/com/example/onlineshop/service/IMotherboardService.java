package com.example.onlineshop.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.onlineshop.entity.Motherboard;

import java.util.List;


/**
 * @author wu
 * 2022.3.28
 */
public interface IMotherboardService extends IService<Motherboard> {

    public IPage<Motherboard> selectMotherboardPage(int pageNum, int pageSize, String param);
    public int addMotherboard(Motherboard motherboard);
    public int editMotherboard(Motherboard motherboard);
    public int delMotherboardById(Integer board_id);
    public List<Motherboard> queryMotherboardList();
    public Motherboard queryMotherboardById(Integer board_id);

    public int lodelMotherboardById(Integer id);

    }


