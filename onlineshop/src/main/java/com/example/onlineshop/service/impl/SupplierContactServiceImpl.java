package com.example.onlineshop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.onlineshop.entity.SupplierContact;
import com.example.onlineshop.mapper.SupplierContactMapper;
import com.example.onlineshop.service.ISupplierContactService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * service实现类
 * @author wu
 * 2022.4.16
 */
@Service
public class SupplierContactServiceImpl extends ServiceImpl<SupplierContactMapper, SupplierContact> implements ISupplierContactService {

    @Resource
    private SupplierContactMapper supplierContactMapper;

    /**
     * 列表查询，全部显示
     */
    @Override
    public List<SupplierContact> querySupplierContactList() {
        return supplierContactMapper.selectList(null);
    }

    /**
     * 分页查询
     */
    @Override
    public IPage<SupplierContact> selectSupplierContactPage(int pageNum, int pageSize, String param) {
        QueryWrapper<SupplierContact> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("is_delete",0);
        if(StringUtils.isNotEmpty(param)){
            queryWrapper
                    .like("name",param);
                    //名称模糊查询

        }
        Page<SupplierContact> page=new Page<>(pageNum,pageSize);
        return supplierContactMapper.selectPage(page,queryWrapper);
    }


    @Override
    public int addSupplierContact(SupplierContact supplierContact) {
        return supplierContactMapper.insert(supplierContact);
    }

    @Override
    public int editSupplierContact(SupplierContact supplierContact) {
        return supplierContactMapper.updateById(supplierContact);
    }

    /**
     * 根据主键删除对象
     */
    @Override
    public int delSupplierContactById(String suppliercontact_id) {
        return supplierContactMapper.deleteById(suppliercontact_id);
    }

    /**
     * 根据主键查询对象
     */
    @Override
    public SupplierContact querySupplierContactById(String suppliercontact_id) {
        return supplierContactMapper.selectById(suppliercontact_id);
    }

    /**
     * 逻辑删除
     * @param id
     * @return 0
     */
    @Override
    public int lodelSupplierContactById(String id){
        return supplierContactMapper.lodelete(id);
    }
    
}

