package com.example.onlineshop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.onlineshop.entity.Case;
import com.example.onlineshop.mapper.CaseMapper;
import com.example.onlineshop.service.ICaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * service实现类
 * @author wu
 * 2022.3.28
 */
@Service
public class CaseServiceImpl extends ServiceImpl<CaseMapper, Case> implements ICaseService {

    @Resource
    private CaseMapper caseMapper;

    /**
     * 列表查询，全部显示
     */
    @Override
    public List<Case> queryCaseList() {
        return caseMapper.selectList(null);
    }

    /**
     * 分页查询
     */
    @Override
    public IPage<Case> selectCasePage(int pageNum, int pageSize, String param) {
        QueryWrapper<Case> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("is_delete",0);
        if(StringUtils.isNotEmpty(param)){
            queryWrapper
                    //.eq("need_id",param)
                    //ID准确查询查询
                    .like("name",param);
                    //名称模糊查询

        }
        Page<Case> page=new Page<>(pageNum,pageSize);
        return caseMapper.selectPage(page,queryWrapper);
    }

    @Override
    public int addCase(Case thecase) {
        return caseMapper.insert(thecase);
    }

    @Override
    public int editCase(Case thecase) {
        return caseMapper.updateById(thecase);
    }

    /**
     * 根据主键删除对象
     */
    @Override
    public int delCaseById(Integer case_id) {
        return caseMapper.deleteById(case_id);
    }

    /**
     * 根据主键查询对象
     */
    @Override
    public Case queryCaseById(Integer case_id) {
        return caseMapper.selectById(case_id);
    }

    /**
     * 逻辑删除
     * @param id
     * @return 0
     */
    @Override
    public int lodelCaseById(Integer id){
        return caseMapper.lodelete(id);
    }

}

