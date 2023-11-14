package com.example.shopping.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.shopping.entity.Ram;

import java.util.List;


/**
 * @author wu
 * 2022.4.4
 */
public interface IRamService extends IService<Ram> {
    public IPage<Ram> selectRamPage(int pageNum, int pageSize, String param);
    public int addRam(Ram ram);
    public int editRam(Ram ram);
    public int delRamById(Integer ram_id);
    public List<Ram> queryRamList();
    public Ram queryRamById(Integer ram_id);

    public int lodelRamById(Integer id);

    }


