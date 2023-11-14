package com.example.shopping.service;


import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 智能装机算法
 * @author wu
 * 2022.4.18
 */
public interface AIACService {
    /************************智能装机-遗传算法************************/

    public String getIteration();
    public String getEnvironmentalCapacity();
    public String getCrossover();
    public String getMutation();
    public String getTechLimit();
    public String getPriceRatio();
    public String getPriceULimit();
    public String getPriceDLimit();

    public void setUserParameters(Integer Budget);

    public void defaultDB();

    public void setGenePool(Integer id,String category);

    public void setGene(Integer id,String limit,String retail);

    public BigDecimal sequentialUnconstrainedMinimizationTechnique(Integer index, Integer generation);

    public void originPopulation();

    public Integer getIndex(Integer generation);

    public void countFitness(Integer generation);

    public void Selection(Integer generation);

    public void Crossover(Integer generation);

    public void Mutation(Integer generation);

    public void apexInherit(Integer generation);

    public List<Double> getTrends();

    public List<String> getIndividual(Integer generation);
    }


