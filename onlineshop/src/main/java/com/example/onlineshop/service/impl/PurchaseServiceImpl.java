package com.example.onlineshop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.onlineshop.entity.Purchase;
import com.example.onlineshop.entity.Worker;
import com.example.onlineshop.mapper.PurchaseMapper;
import com.example.onlineshop.service.IPurchaseService;
import com.example.onlineshop.service.RedisService;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * service实现类
 * @author wu
 * 2022.4.20
 */
@Service
public class PurchaseServiceImpl extends ServiceImpl<PurchaseMapper, Purchase> implements IPurchaseService {

    @Resource
    private PurchaseMapper purchaseMapper;

    @Resource
    private RedisService redisService;

    /**
     * 列表查询，全部显示
     */
    @Override
    public List<Purchase> queryPurchaseList() {
        return purchaseMapper.selectList(null);
    }

    /**
     * 分页查询
     */
    @Override
    public IPage<Purchase> selectPurchasePage(int pageNum, int pageSize, String param) {

        QueryWrapper<Purchase> queryWrapper=new QueryWrapper<>();
        if (redisService.getJobRole(((Worker) SecurityUtils.getSubject().getPrincipal()).getJob(),"purchaseManage")){

        }else {
            queryWrapper.eq("worker_purchase_id",((Worker) SecurityUtils.getSubject().getPrincipal()).getId());
        }
        if(StringUtils.isNotEmpty(param)){
            queryWrapper
                    .like("goods_name",param);
                    //名称模糊查询

        }
        Page<Purchase> page=new Page<>(pageNum,pageSize);
        return purchaseMapper.selectPage(page,queryWrapper);
    }

    @Override
    public int addPurchase(Purchase purchase) {
        return purchaseMapper.insert(purchase);
    }

    @Override
    public int editPurchase(Purchase purchase) {
        return purchaseMapper.updateById(purchase);
    }

    /**
     * 根据主键删除对象
     */
    @Override
    public int delPurchaseById(Integer purchase_id) {
        return purchaseMapper.deleteById(purchase_id);
    }

    /**
     * 根据主键查询对象
     */
    @Override
    public Purchase queryPurchaseById(Integer purchase_id) {
        return purchaseMapper.selectById(purchase_id);
    }

    /**
     * 逻辑删除（撤销）
     */
    @Override
    public void lodelete(Integer id){
        purchaseMapper.lodelete(id);
    }

    /**
     * 打回（审核）
     */
    @Override
    public void refuse(Integer id){
        purchaseMapper.refuse(id);
    }

    /**
     * 批准（审核）
     */
    @Override
    public void examine(Integer id){
        purchaseMapper.examine(id);
    }

    /**
     * 完成（收货）
     */
    @Override
    public void finish(Integer id){
        purchaseMapper.finish(id);
    }

}

