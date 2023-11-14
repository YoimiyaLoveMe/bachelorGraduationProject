package com.example.onlineshop.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.onlineshop.entity.Power;

import java.util.List;


/**
 * @author wu
 * 2022.3.28
 */
public interface IPowerService extends IService<Power> {
    public IPage<Power> selectPowerPage(int pageNum, int pageSize, String param);
    public int addPower(Power power);
    public int editPower(Power power);
    public int delPowerById(Integer power_id);
    public List<Power> queryPowerList();
    public Power queryPowerById(Integer power_id);

    public int lodelPowerById(Integer id);

    }


