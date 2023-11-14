package com.example.onlineshop.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 员工实体类
 * @author wu
 * 2022.3
 */
@Data
@TableName(value="worker")
public class Worker implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id")
    private String id;

    private String name;

    private String password;

    @TableField(value = "identity_card")
    private String idCard;

    private String sex;

    @TableField(value = "job_id")
    private Integer job;

    private String department;

    private Integer working;


}
