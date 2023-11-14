package com.example.shopping.controller;

import com.example.shopping.common.ResultMapUtil;
import com.example.shopping.entity.*;
import com.example.shopping.service.*;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 算法相关controller
 * @author wu
 * 2022.4.27
 */
@Controller
@RequestMapping(value = "/algorithm")
public class AlgorithmController {

    /**
     * aiac
     */
    @Resource
    private AIACService aiacService;

    /**
     * Mysql
     * goods
     */
    @Resource
    private IMotherboardService motherboardService;
    @Resource
    private ICpuService cpuService;
    @Resource
    private IGraphicscardService graphicscardService;
    @Resource
    private IRamService ramService;
    @Resource
    private ISsdService ssdService;
    @Resource
    private IHeatsinkService heatsinkService;
    @Resource
    private IPowerService powerService;
    @Resource
    private ICaseService caseService;

    /**
     * AIAC
     * 智能装机算法
     * @param grade 需求定位
     * @param budget 预算
     * @param requirement 性能要求（以复选框筛选出最高要求）
     * @param preference 偏好（性价比/性能） "on/null"
     * @return combination.html 整机展示页面(弹出窗)
     */
    @RequestMapping(value = "/AIAC")
    @ResponseBody
    public Object ArtificialIntelligenceAssembleComputer(String grade, Integer budget, Integer requirement, String preference, Model model){

        /**
         * 算法开始，清除过往数据
         */
        aiacService.defaultDB();
        /**
         * 依据输入的用户要求，aiac写入基因库和基因详情
         * budget不大于5000不使用独立显卡
         */
        /**
         * 设定预算
         */
        aiacService.setUserParameters(budget);
        /**
         * 写入基因库和基因详情
         */
        //主板
        List<Motherboard> motherboardList = motherboardService.queryMotherboardList();
        for (Motherboard motherboard:motherboardList){
            //基因库
            aiacService.setGenePool(motherboard.getId(),"motherboard");
            //基因详情
            aiacService.setGene(motherboard.getId(),"cpuSlot",motherboard.getCpuSlot());
            aiacService.setGene(motherboard.getId(),"memoryType",motherboard.getMemoryType());
            aiacService.setGene(motherboard.getId(),"memoryVolume",motherboard.getMemoryVolume().toString());
            aiacService.setGene(motherboard.getId(),"pcie",motherboard.getPciE().toString());
            aiacService.setGene(motherboard.getId(),"storageInterface",motherboard.getStorageInterface());
            aiacService.setGene(motherboard.getId(),"price",motherboard.getSalePrice().toString());
        }
        //芯片
        List<Cpu> cpuList = cpuService.queryCpuList();
        for (Cpu cpu:cpuList){
            aiacService.setGenePool(cpu.getId(),"cpu");
            aiacService.setGene(cpu.getId(),"cpuSlot",cpu.getSlot());
            aiacService.setGene(cpu.getId(),"tdp",cpu.getTdp().toString());
            aiacService.setGene(cpu.getId(),"mtp",cpu.getMtp().toString());
            aiacService.setGene(cpu.getId(),"memoryType",cpu.getMemoryType());
            aiacService.setGene(cpu.getId(),"memoryVolume",cpu.getMemoryVolume().toString());
            aiacService.setGene(cpu.getId(),"memoryChannel",cpu.getMemoryChannel().toString());
            aiacService.setGene(cpu.getId(),"pcie",cpu.getPciE().toString());
            aiacService.setGene(cpu.getId(),"price",cpu.getSalePrice().toString());
            aiacService.setGene(cpu.getId(),"performance",cpu.getPerformance().toString());
        }
        //显卡
        List<Graphicscard> graphicscardList = graphicscardService.queryGraphicscardList();
        for (Graphicscard graphicscard:graphicscardList){
            aiacService.setGenePool(graphicscard.getId(),"graphicscard");
            aiacService.setGene(graphicscard.getId(),"pcie",graphicscard.getPciE().toString());
            aiacService.setGene(graphicscard.getId(),"powerWaste",graphicscard.getPowerWaste().toString());
            aiacService.setGene(graphicscard.getId(),"size",graphicscard.getSize().toString());
            aiacService.setGene(graphicscard.getId(),"price",graphicscard.getSalePrice().toString());
            aiacService.setGene(graphicscard.getId(),"performance",graphicscard.getPerformance().toString());
        }
        //内存
        List<Ram> ramList = ramService.queryRamList();
        for (Ram ram:ramList){
            aiacService.setGenePool(ram.getId(),"ram");
            aiacService.setGene(ram.getId(),"volume",ram.getVolume().toString());
            aiacService.setGene(ram.getId(),"pack",ram.getPack().toString());
            aiacService.setGene(ram.getId(),"type",ram.getType());
            aiacService.setGene(ram.getId(),"price",ram.getSalePrice().toString());
            aiacService.setGene(ram.getId(),"performance",ram.getPerformance().toString());
        }
        //硬盘
        List<Ssd> ssdList = ssdService.querySsdList();
        for (Ssd ssd:ssdList){
            aiacService.setGenePool(ssd.getId(),"ssd");
            aiacService.setGene(ssd.getId(),"slot",ssd.getSlot());
            aiacService.setGene(ssd.getId(),"price",ssd.getSalePrice().toString());
            aiacService.setGene(ssd.getId(),"performance",ssd.getPerformance().toString());
        }
        //散热器
        List<Heatsink> heatsinkList = heatsinkService.queryHeatsinkList();
        for (Heatsink heatsink:heatsinkList){
            aiacService.setGenePool(heatsink.getId(),"heatsink");
            aiacService.setGene(heatsink.getId(),"cfm",heatsink.getCfm().toString());
            aiacService.setGene(heatsink.getId(),"size",heatsink.getSize().toString());
            aiacService.setGene(heatsink.getId(),"price",heatsink.getSalePrice().toString());
        }
        //电源
        List<Power> powerList = powerService.queryPowerList();
        for (Power power:powerList){
            aiacService.setGenePool(power.getId(),"power");
            aiacService.setGene(power.getId(),"ratedPower",power.getRatedPower().toString());
            aiacService.setGene(power.getId(),"price",power.getSalePrice().toString());
        }
        //机箱
        List<Case> caseList = caseService.queryCaseList();
        for (Case box:caseList){
            aiacService.setGenePool(box.getId(),"thecase");
            aiacService.setGene(box.getId(),"heatsinkLimit",box.getHtLimit().toString());
            aiacService.setGene(box.getId(),"gcLimit",box.getGcLimit().toString());
            aiacService.setGene(box.getId(),"price",box.getSalePrice().toString());
        }
        /**
         * 随机生成初代种群
         */
        aiacService.originPopulation();
        Integer Iteration = Integer.parseInt(aiacService.getIteration());
        aiacService.countFitness(0);
        for (int i =1 ;i<Iteration+1;i++){
            /**
             * 新一轮迭代开始
             */
            /**
             * 选择
             */
            aiacService.Selection(i);
            /**
             * 杂交
             */
            aiacService.Crossover(i);
            /**
             * 突变
             */
            aiacService.Mutation(i);
            /**
             * 由染色体搜索基因，统计适应度得分
             */
            aiacService.countFitness(i);
            /**
             * 最优个体保存
             */
            aiacService.apexInherit(i);
            /**
             * 结束本轮迭代
             */
        }
        /**
         * 达到最大迭代次数，输出最优个体和每代最优个体(统计用)
         * 本来Map<个体序号，个体适应度>，但map类型若key相同则会错误丢失数据
         * 改为Map<代，个体适应度>更好输出
         */
        List<Double> trends = aiacService.getTrends();
        return trends;
    }

    @RequestMapping(value = "/getIndividual")
    @ResponseBody
    public List<String> getIndividual(Integer generation){
        List<String> individual = aiacService.getIndividual(generation);
        return individual;
    }
}
