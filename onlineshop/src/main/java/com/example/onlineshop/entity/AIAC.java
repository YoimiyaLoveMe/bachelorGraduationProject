package com.example.onlineshop.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 智能装机算法参数实体类
 * @author wu
 * 2022.4.29
 */
@Data

public class AIAC implements Serializable {

    private Integer Iteration;

    private Integer environmentalCapacity;

    private String Crossover;

    private String Mutation;

    //sequentialUnconstrainedMinimizationTechnique

    private String techLimit;

    private String priceRatio;

    private String priceULimit;

    private String priceDLimit;
}
