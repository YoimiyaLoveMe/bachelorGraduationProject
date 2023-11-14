package com.example.onlineshop.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 供应商联系人实体类
 * @author wu
 */
@Data
@TableName(value="supplier_contact")
public class SupplierContact implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id")
    private String id;

    private String name;

    private Integer belong;

    private String information;

}
