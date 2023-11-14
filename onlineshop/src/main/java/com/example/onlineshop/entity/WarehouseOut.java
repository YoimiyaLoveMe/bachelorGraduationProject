package com.example.onlineshop.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 出库单实体类
 * @author wu
 */
@Data
@TableName(value="warehouse_out")
public class WarehouseOut implements Serializable {
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

    @TableField(value = "warehouse_id")
    private Integer warehouseId;

    private Integer number;

    @TableField(value = "worker_id")
    private String workerId;
}
