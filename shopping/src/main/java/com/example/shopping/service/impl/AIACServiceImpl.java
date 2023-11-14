package com.example.shopping.service.impl;

import com.example.shopping.common.JedisUtil;
import com.example.shopping.service.AIACService;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.math.BigDecimal;
import java.util.*;

/**
 * 智能装机算法
 * service实现类
 * @author wu
 * 2022.4.18
 */
@Service
public class AIACServiceImpl implements AIACService {

    /*****************************************************************************************/
    /**智能装机算法*/

    /**
     * 枚举类
     */

    /**
     * 全硬件类
     */
    enum Category{
        motherboard,cpu,graphicscard,ram,ssd,heatsink,power,thecase;
    }

    /**
     * 低端电脑无独显硬件类
     */
    enum MinCategory{
        motherboard,cpu,ram,ssd,heatsink,power,thecase;
    }

    /**
     * 有性能评分硬件类
     */
    enum PerformanceCategory{
        cpu,graphicscard,ram,ssd;
    }

    @Override
    public String getIteration(){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(3);
        String Iteration = jedis.get("Iteration");
        jedis.close();
        return Iteration;
    }

    @Override
    public String getEnvironmentalCapacity(){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(3);
        String environmentalCapacity = jedis.get("environmentalCapacity");
        jedis.close();
        return environmentalCapacity;
    }

    @Override
    public String getCrossover(){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(3);
        String Crossover = jedis.get("Crossover");
        jedis.close();
        return Crossover;
    }

    @Override
    public String getMutation(){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(3);
        String Mutation = jedis.get("Mutation");
        jedis.close();
        return Mutation;
    }

    @Override
    public String getTechLimit(){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(3);
        String techLimit = jedis.get("techLimit");
        jedis.close();
        return techLimit;
    }

    @Override
    public String getPriceRatio(){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(3);
        String priceRatio = jedis.get("priceRatio");
        jedis.close();
        return priceRatio;
    }

    @Override
    public String getPriceULimit(){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(3);
        String priceULimit = jedis.get("priceULimit");
        jedis.close();
        return priceULimit;
    }

    @Override
    public String getPriceDLimit(){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(3);
        String priceDLimit = jedis.get("priceDLimit");
        jedis.close();
        return priceDLimit;
    }

    /**
     * 设定用户参数
     */
    @Override
    public void setUserParameters(Integer Budget){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(3);
        jedis.set("Budget",Budget.toString());
        jedis.close();
    }

    /**
     * 算法运行开始
     * 初始化数据库 : 清除存储内容，设置基础参数
     */
    @Override
    public void defaultDB(){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(3);
        //基础参数保存
        String Iteration = jedis.get("Iteration");
        String environmentalCapacity = jedis.get("environmentalCapacity");
        String Crossover = jedis.get("Crossover");
        String Mutation = jedis.get("Mutation");
        String techLimit = jedis.get("techLimit");
        String priceRatio = jedis.get("priceRatio");
        String priceULimit = jedis.get("priceULimit");
        String priceDLimit = jedis.get("priceDLimit");
        //清除数据库
        jedis.flushDB();
        //再次写入基础参数
        jedis.set("Iteration",Iteration);
        jedis.set("environmentalCapacity",environmentalCapacity);
        jedis.set("Crossover",Crossover);
        jedis.set("Mutation",Mutation);
        jedis.set("techLimit",techLimit);
        jedis.set("priceRatio",priceRatio);
        jedis.set("priceULimit",priceULimit);
        jedis.set("priceDLimit",priceDLimit);
        jedis.close();
    }

    /**
     * 设定基因库
     */
    @Override
    public void setGenePool(Integer id,String category){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(3);
        jedis.sadd(category,id.toString());
        jedis.close();
    }

    /**
     * 写入基因详情
     */
    @Override
    public void setGene(Integer id,String limit,String retail){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(3);
        jedis.hset(id.toString(),limit,retail);
        jedis.close();
    }

    /**
     * 约束性检查-价格偏移
     */

    /**
     * 约束性检查-兼容性
     * 罚函数
     */
    @Override
    public BigDecimal sequentialUnconstrainedMinimizationTechnique(Integer index, Integer generation){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(3);
        //主板
        String mId = jedis.hget("generation:"+generation+":index:"+index,"motherboard");
        String mCpuSlot = jedis.hget(mId,"cpuSlot");
        //String mBoardType = jedis.hget(mId,"boardType");
        String mMemoryType = jedis.hget(mId,"memoryType");
        Integer mMemoryVolume = Integer.parseInt(jedis.hget(mId,"memoryVolume"));
        Double mPCIE = Double.parseDouble(jedis.hget(mId,"pcie"));
        String mStorageInterface = jedis.hget(mId,"storageInterface");
        //String mPowerInterface = jedis.hget(mId,"powerInterface");
        //芯片
        String cId = jedis.hget("generation:"+generation+":index:"+index,"cpu");
        String cCpuSlot = jedis.hget(cId,"cpuSlot");
        Integer cTDP = Integer.parseInt(jedis.hget(cId,"tdp"));
        Integer cMTP = Integer.parseInt(jedis.hget(cId,"mtp"));
        String cMemoryType = jedis.hget(cId,"memoryType");
        Integer cMemoryVolume = Integer.parseInt(jedis.hget(cId,"memoryVolume"));
        Integer cMemoryChannel = Integer.parseInt(jedis.hget(cId,"memoryChannel"));
        Double cPCIE = Double.parseDouble(jedis.hget(cId,"pcie"));
        //显卡
        String gId = jedis.hget("generation:"+generation+":index:"+index,"graphicscard");
        Double gPCIE = Double.parseDouble(jedis.hget(gId,"pcie"));
        Integer gPowerWaste = Integer.parseInt(jedis.hget(gId,"powerWaste"));
        //Integer gPowerInterface = Integer.parseInt(jedis.hget(gId,"powerInterface"));
        Integer gSize = Integer.parseInt(jedis.hget(gId,"size"));
        //内存
        String rId = jedis.hget("generation:"+generation+":index:"+index,"ram");
        Integer rVolume = Integer.parseInt(jedis.hget(rId,"volume"));
        Integer rPack = Integer.parseInt(jedis.hget(rId,"pack")) ;
        String rType = jedis.hget(rId,"type");
        //String rBasicFrequency = jedis.hget(rId,"basicFrequency");
        //String rSlot = jedis.hget(rId,"slot");
        //硬盘
        String sId = jedis.hget("generation:"+generation+":index:"+index,"ssd");
        String sSlot = jedis.hget(sId,"slot");
        //散热器
        String hId = jedis.hget("generation:"+generation+":index:"+index,"heatsink");
        Integer hCFM = Integer.parseInt(jedis.hget(hId,"cfm"));
        Integer hSize = Integer.parseInt(jedis.hget(hId,"size"));
        //电源
        String pId = jedis.hget("generation:"+generation+":index:"+index,"power");
        Integer pRatedPower = Integer.parseInt(jedis.hget(pId,"ratedPower"));
        //Integer pGCInterface = Integer.parseInt(jedis.hget(pId,"gcInterface"));
        //Integer pSSDInterface = Integer.parseInt(jedis.hget(pId,"ssdInterface"));
        //Integer pSize = Integer.parseInt(jedis.hget(pId,"size"));
        //机箱
        String bId = jedis.hget("generation:"+generation+":index:"+index,"thecase");
        //String bMbLimit = jedis.hget(bId,"motherboardLimit");
        Integer bHtLimit = Integer.parseInt(jedis.hget(bId,"heatsinkLimit"));
        Integer bGcLimit = Integer.parseInt(jedis.hget(bId,"gcLimit"));
        //Integer bPLimit = Integer.parseInt(jedis.hget(bId,"powerLimit"));
        //兼容性检查
        Double ratio = Double.valueOf(1);
        Double techLimit = Double.parseDouble(getTechLimit());
        //主板
        if(mCpuSlot.equals(cCpuSlot)){
        }else {
            ratio*=techLimit;
        }
        if(mMemoryType.equals(cMemoryType) && mMemoryType.equals(rType) && cMemoryType.equals(rType)){
        }else {
            ratio*=techLimit;
        }
        if(mPCIE.equals(cPCIE) && mPCIE.equals(gPCIE) && cPCIE.equals(gPCIE)){
        }else {
            ratio*=techLimit;
        }
        if(mMemoryVolume >= rVolume*rPack && cMemoryVolume >= rVolume*rPack){
        }else {
            ratio*=techLimit;
        }
        if(mStorageInterface.equals(sSlot)){
        }else {
            ratio*=techLimit;
        }
        if((160+cMTP+gPowerWaste)>=pRatedPower){
        }else {
            ratio*=techLimit;
        }
        if(cTDP<hCFM){
        }else {
            ratio*=techLimit;
        }
        if(cMemoryChannel*2>rPack){
        }else {
            ratio*=techLimit;
        }
        if (gSize<=bGcLimit){
        }else {
            ratio*=techLimit;
        }
        if(hSize<=bHtLimit){
        }else {
            ratio*=techLimit;
        }
        jedis.close();
        BigDecimal Ratio = new BigDecimal(ratio);
        return Ratio;
    }

    /**
     * 生成初始种群(有独显)
     * 代数从0开始
     */
    @Override
    public void originPopulation(){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(3);
        //查询基础参数：环境容纳量
        Integer capacity = Integer.parseInt(jedis.get("environmentalCapacity"));
        for(int i = 1;i<capacity+1;i++){
            for (Category category : Category.values()){
                jedis.hset("generation:0:index:"+i,category.toString(),jedis.srandmember(category.toString()));
            }
        }
        jedis.close();
    }

    /**
     * 一代个体分配序号
     * 从1开始
     */
    @Override
    public Integer getIndex(Integer generation){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(3);
        jedis.incr("index:generation:"+generation);
        Integer index =  Integer.parseInt(jedis.get("index:generation:"+generation));
        jedis.close();
        return index;
    }

    /**
     * 一代个体适应度评分并统计(有独显)
     */
    @Override
    public void countFitness(Integer generation){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(3);
        //获得本代个体数
        Integer number = 0;
        //0代原始种群数量等于环境容纳量
        if (generation == 0){
            number = Integer.parseInt(jedis.get("environmentalCapacity"));
        }else {
            number = Integer.parseInt(jedis.get("index:generation:"+generation));
        }
        //按序号遍历
        for (int i = 1;i<number+1;i++){
            //性能统计
            Integer performance = 0;
            for (PerformanceCategory performanceCategory: PerformanceCategory.values()){
                String id = jedis.hget("generation:"+generation+":index:"+i,performanceCategory.toString());
                performance += Integer.parseInt(jedis.hget(id,"performance"));
            }
            //价格统计
            BigDecimal price = BigDecimal.ZERO;
            for (Category category : Category.values()){
                //获得对应硬件的id
                String id = jedis.hget("generation:"+generation+":index:"+i,category.toString());
                //依据id查询价格并统计(方法计算会返回一个新的对象，而不会改变原对象，所以需要重新赋值)
                price = price.add(new BigDecimal(jedis.hget(id,"price")));
            }
            //计算适应度(性价比)
            BigDecimal Performance = new BigDecimal(performance);
            //Performance.divide(price,2,BigDecimal.ROUND_DOWN)：向下取保留2位小数
            BigDecimal fitness = Performance.divide(price,10,BigDecimal.ROUND_DOWN);
            //约束性检查(兼容性)
            BigDecimal ratio = sequentialUnconstrainedMinimizationTechnique(i,generation);
            fitness = fitness.multiply(ratio);
            //价格偏移检查
            Integer budget = Integer.parseInt(jedis.get("Budget"));
            BigDecimal uLimit = new BigDecimal(budget*(1+Double.parseDouble(getPriceRatio())));
            BigDecimal dLimit = new BigDecimal(budget*(1-Double.parseDouble(getPriceRatio())));
            if(price.compareTo(uLimit) == 1 || price.compareTo(dLimit) == -1){
                fitness = BigDecimal.ZERO;
            }
            else if (price.compareTo(new BigDecimal(budget)) == 1){
                fitness = fitness.subtract(fitness.multiply(new  BigDecimal(getPriceULimit()).multiply(uLimit.subtract(new BigDecimal(budget))).divide(uLimit.subtract(price),10,BigDecimal.ROUND_DOWN)));
            }else if(price.compareTo(new BigDecimal(budget)) == -1){
                fitness = fitness.subtract(fitness.multiply(new  BigDecimal(getPriceDLimit()).multiply(new BigDecimal(budget).subtract(dLimit)).divide(price.subtract(dLimit),10,BigDecimal.ROUND_UP)));
            }
            //添加至sorted_set中
            jedis.zadd("generation:"+generation,fitness.doubleValue(),String.valueOf(i));
            //将一代个体的适应度得分累计(用于选择过程的蒙特卡罗法)
        }
        jedis.close();
    }

    /**
     * 选择(复制)(有独显)
     * Selection
     */
    @Override
    public void Selection(Integer generation){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(3);
        Random random = new Random();
        //获得环境容纳量
        Integer capacity = Integer.parseInt(jedis.get("environmentalCapacity"));
        //获得父代未被淘汰个体
        Set<Tuple> set = jedis.zrevrangeWithScores("generation:"+(generation-1),0,capacity-1);
        //1.蒙特卡罗法：获得父代未被淘汰个体适应度总和
        Double sumFitness = Double.valueOf(0);
        for (Tuple tuple:set){
            sumFitness += tuple.getScore();
        }
        //2.轮盘赌选择策略：获得略多余环境容纳量的染色体
        for (int i =0;i< capacity+10;i++){
            //生成[0,sumFitness]范围的随机数
            double r = random.nextDouble() * sumFitness;
            //查找父代轮盘，获得对应的个体
            for (Tuple tuple:set){
                r -= tuple.getScore();
                //落入区间，结束循环，当前tuple即是被选中的个体
                if (r<0){
                    Integer index = getIndex(generation);
                    //生成本代个体，继承对应父代基因
                    for (Category category: Category.values()){
                        jedis.hset("generation:"+generation+":index:"+index,category.toString(),jedis.hget("generation:"+(generation-1)+":index:"+tuple.getElement(),category.toString()));
                    }
                    //将新个体id同步添加至list用于交叉操作
                    jedis.lpush("selection:generation:"+generation, index.toString());
                    break;
                }
            }
            //获得本代个体序号

        }
        jedis.close();
    }

    /**
     * 交叉(有独显)
     * Crossover
     */
    @Override
    public void Crossover(Integer generation){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(3);
        Random random = new Random();
        //获得交叉率
        Double Crossover = Double.parseDouble(jedis.get("Crossover"));
        //遍历列表
        for (int i =1;i<jedis.llen("selection:generation:"+generation);i+=2){
            double r = random.nextDouble();
            //满足概率进行交叉
            if(r<= Crossover){
                //获取交叉个体
                String indexL = jedis.lpop("selection:generation:"+generation);
                String indexR = jedis.rpop("selection:generation:"+generation);
                //均匀交叉
                for (Category category: Category.values()){
                    Boolean flag = random.nextBoolean();
                    //由于redis采用单线程，所以赋值嵌套查询会互相影响
                    String leftGene = jedis.hget("generation:"+generation+":index:"+indexL,category.toString());
                    String rightGene = jedis.hget("generation:"+generation+":index:"+indexR,category.toString());
                    //发生互换
                    if(flag){
                        jedis.hset("generation:"+generation+":index:"+indexL,category.toString(),rightGene);
                        jedis.hset("generation:"+generation+":index:"+indexR,category.toString(),leftGene);
                    }else {
                    }
                }
            }
        }
        jedis.close();
    }


    /**
     * 变异(有独显)
     * Mutation
     */
    @Override
    public void Mutation(Integer generation) {
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(3);
        Random random = new Random();
        //查询本代个体数
        Double number = Double.parseDouble( jedis.get("index:generation:"+generation));
        //获得变异率
        Double Mutation = Double.parseDouble(jedis.get("Mutation"));
        //按照变异率随机获得变异个体数
        Integer check =0;
        for (int i= 0;i<number;i++){
            Double chance = random.nextDouble();
            if(chance<Mutation){
                check+=1;
            }
        }
        //变异
        for (int i =0 ;i<check;i++){
            //随机挑选一个个体
            Integer index = (int)(random.nextDouble()*number);
            //位点变异
            Double flag = random.nextDouble();
            for (Category category: Category.values()){
                flag -=0.125;
                if(flag < 0){
                    List<String> id = jedis.srandmember(category.toString(),1);
                    jedis.hset("generation:"+generation+":index:"+index,category.toString(),id.get(0));
                    break;
                }
            }
        }
        jedis.close();
    }


    /**
     * 最优个体保存(有独显)
     */
    @Override
    public void apexInherit(Integer generation){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(3);
        //获取父代最优个体
        Set<Tuple> set = jedis.zrevrangeWithScores("generation:"+(generation-1),0,0);
        //假循环,只有一次
        for (Tuple tuple :set){
            //获取本代个体序号
            Integer index = getIndex(generation);
            //遍历基因
            for (Category category: Category.values()){
                //继承基因
                jedis.hset("generation:"+generation+":index:"+index,category.toString(),jedis.hget("generation:"+(generation-1)+":index:"+tuple.getElement(),category.toString()));
            }
            //将该个体加入至本代个体集合中
            jedis.zadd("generation:"+generation,tuple.getScore(),index.toString());
        }
        jedis.close();
    }

    /**
     * 统计每代最优个体
     */
    @Override
    public List<Double> getTrends(){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(3);
        List<Double> trends = new ArrayList<>();
        Integer Iteration = Integer.parseInt(jedis.get("Iteration"));
        for (int i = 0;i<Iteration+1;i++){
            Set<Tuple> apex = jedis.zrevrangeWithScores("generation:"+i,0,0);
            //假循环，只有一次
            for (Tuple tuple:apex){
                trends.add(tuple.getScore());
            }
        }
        jedis.close();
        return trends;
    }

    /**
     * 获得最优个体
     */
    @Override
    public List<String> getIndividual(Integer generation){
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(3);
        Set<Tuple> apex = jedis.zrevrangeWithScores("generation:"+generation,0,0);
        List<String> individual = new ArrayList<>();
        for (Tuple tuple:apex){
            for (Category category:Category.values()){
                individual.add(jedis.hget("generation:"+generation+":index:"+tuple.getElement(),category.toString()));
            }
        }
        jedis.close();
        return individual;
    }
}

