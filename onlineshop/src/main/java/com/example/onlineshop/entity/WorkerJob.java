package com.example.onlineshop.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 职位实体类
 * @author wu
 * 2022.4.13
 */
@Data
@TableName(value="worker_job")
public class WorkerJob implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id")
    private Integer id;

    private String name;

    private String department;

    @TableField(value = "editor_id")
    private String editor;

}
