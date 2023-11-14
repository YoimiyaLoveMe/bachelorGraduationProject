package com.example.shopping.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 顾客实体类
 * @author wu
 * 2022.4
 */
@Data
@TableName(value="customer")
public class Customer implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id")
    private String id;

    private String name;

    private String password;


}
