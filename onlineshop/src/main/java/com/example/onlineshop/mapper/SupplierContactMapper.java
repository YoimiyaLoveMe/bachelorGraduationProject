package com.example.onlineshop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.onlineshop.entity.SupplierContact;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * @author wu
 * 2022.4.16
 */
@Mapper
public interface SupplierContactMapper extends BaseMapper<SupplierContact> {
    @Update("UPDATE supplier_contact SET is_delete = 1 WHERE id = #{id}")
    int lodelete(String id);

}
