package com.example.shopping.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.shopping.entity.Case;

import java.util.List;


/**
 * @author wu
 * 2022.3.28
 */
public interface ICaseService extends IService<Case> {
    public IPage<Case> selectCasePage(int pageNum, int pageSize, String param);
    public int addCase(Case thecase);
    public int editCase(Case thecase);
    public int delCaseById(Integer case_id);
    public List<Case> queryCaseList();
    public Case queryCaseById(Integer case_id);

    public int lodelCaseById(Integer id);

    }


