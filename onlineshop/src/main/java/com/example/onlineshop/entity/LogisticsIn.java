package com.example.onlineshop.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 收货表实体类
 * @author wu
 */
@Data
@TableName(value="logistics_in")
public class LogisticsIn implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id")
    private Integer id;

    @TableField(value = "source_category")
    private String sourceCategory;

    @TableField(value = "source_id")
    private Integer sourceId;

    @TableField(value = "goods_id")
    private Integer goodsId;

    @TableField(value = "goods_name")
    private String goodsName;

    private Integer number;

    @TableField(value = "logistics_number")
    private String logisticsNumber;

    @TableField(value = "worker_id")
    private String workerId;


}
