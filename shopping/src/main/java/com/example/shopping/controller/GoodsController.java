package com.example.shopping.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.shopping.common.ResultMapUtil;
import com.example.shopping.entity.*;
import com.example.shopping.service.*;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

@Controller
@RequestMapping(value = "/goods")//对应前端ajax的url，是一个接口，也和index中的lay-href相同

/**
 * @author wu
 * 2022.4.7
 * 商城控制类
 */
public class GoodsController {

    @RequestMapping
    public String index(){
        return "/index";
        //执行请求后转向的前端页面地址
    }

    /**
     * redis通用操作
     * 2022.4.1
     */

    @Resource
    private RedisService redisService;

    /**
     * 查询商品销量
     * 2022.4.8
     */
    @RequestMapping(value="/getSaleById")
    @ResponseBody
    public Integer getSaleById(@RequestParam(name="id")Integer id) {
        Integer sale = redisService.getSaleById(id);
        return sale;
    }

    /**
     * 查询商品库存
     * 2022.4.8
     */
    @RequestMapping(value="/getStockById")
    @ResponseBody
    public Integer getStockById(@RequestParam(name="id")Integer id) {
        Integer stock = redisService.getStockById(id);
        return stock;
    }

    /*******************************************************************************************************************/

    /**
     * controller内部通用方法
     * 根据id查询商品类别并输出对象
     * 2022.4.21
     */
    public GoodsCom productObject(Integer id){
        /**
         * redis查询商品分类
         * @param id
         * @return category
         */
        String category = redisService.getGoodsCategory(id);
        /**
         * 实例通用对象用于输出
         */
        GoodsCom goodsCom = new GoodsCom();
        /**
         * 根据商品类至对应商品表查询商品详细信息并转移输出对象
         * @param category
         * @return goodsCom
         */
        switch (category){
            case "motherboard":
                Motherboard motherboard = motherboardService.queryMotherboardById(id);
                goodsCom.setId(motherboard.getId());
                goodsCom.setName(motherboard.getName());
                goodsCom.setBrand(motherboard.getBrand());
                goodsCom.setPicture(motherboard.getPicture());
                goodsCom.setPrice(motherboard.getPrice());
                goodsCom.setSalePrice(motherboard.getSalePrice());
                goodsCom.setInformation(motherboard.getInformation());
                goodsCom.setCategory("motherboard");
                break;
            case "cpu":
                Cpu cpu = cpuService.queryCpuById(id);
                goodsCom.setId(cpu.getId());
                goodsCom.setName(cpu.getName());
                goodsCom.setBrand(cpu.getBrand());
                goodsCom.setPicture(cpu.getPicture());
                goodsCom.setPrice(cpu.getPrice());
                goodsCom.setSalePrice(cpu.getSalePrice());
                goodsCom.setInformation(cpu.getInformation());
                goodsCom.setCategory("cpu");
                break;
            case "graphicscard":
                Graphicscard graphicscard = graphicscardService.queryGraphicscardById(id);
                goodsCom.setId(graphicscard.getId());
                goodsCom.setName(graphicscard.getName());
                goodsCom.setBrand(graphicscard.getBrand());
                goodsCom.setPicture(graphicscard.getPicture());
                goodsCom.setPrice(graphicscard.getPrice());
                goodsCom.setSalePrice(graphicscard.getSalePrice());
                goodsCom.setInformation(graphicscard.getInformation());
                goodsCom.setCategory("graphicscard");
                break;
            case "ram":
                Ram ram = ramService.queryRamById(id);
                goodsCom.setId(ram.getId());
                goodsCom.setName(ram.getName());
                goodsCom.setBrand(ram.getBrand());
                goodsCom.setPicture(ram.getPicture());
                goodsCom.setPrice(ram.getPrice());
                goodsCom.setSalePrice(ram.getSalePrice());
                goodsCom.setInformation(ram.getInformation());
                goodsCom.setCategory("ram");
                break;
            case "ssd":
                Ssd ssd = ssdService.querySsdById(id);
                goodsCom.setId(ssd.getId());
                goodsCom.setName(ssd.getName());
                goodsCom.setBrand(ssd.getBrand());
                goodsCom.setPicture(ssd.getPicture());
                goodsCom.setPrice(ssd.getPrice());
                goodsCom.setSalePrice(ssd.getSalePrice());
                goodsCom.setInformation(ssd.getInformation());
                goodsCom.setCategory("ssd");
                break;
            case "heatsink":
                Heatsink heatsink = heatsinkService.queryHeatsinkById(id);
                goodsCom.setId(heatsink.getId());
                goodsCom.setName(heatsink.getName());
                goodsCom.setBrand(heatsink.getBrand());
                goodsCom.setPicture(heatsink.getPicture());
                goodsCom.setPrice(heatsink.getPrice());
                goodsCom.setSalePrice(heatsink.getSalePrice());
                goodsCom.setInformation(heatsink.getInformation());
                goodsCom.setCategory("heatsink");
                break;
            case "power":
                Power power = powerService.queryPowerById(id);
                goodsCom.setId(power.getId());
                goodsCom.setName(power.getName());
                goodsCom.setBrand(power.getBrand());
                goodsCom.setPicture(power.getPicture());
                goodsCom.setPrice(power.getPrice());
                goodsCom.setSalePrice(power.getSalePrice());
                goodsCom.setInformation(power.getInformation());
                goodsCom.setCategory("power");
                break;
            case "case":
                Case thecase = caseService.queryCaseById(id);
                goodsCom.setId(thecase.getId());
                goodsCom.setName(thecase.getName());
                goodsCom.setBrand(thecase.getBrand());
                goodsCom.setPicture(thecase.getPicture());
                goodsCom.setPrice(thecase.getPrice());
                goodsCom.setSalePrice(thecase.getSalePrice());
                goodsCom.setInformation(thecase.getInformation());
                goodsCom.setCategory("case");
                break;
            default: return null;
        }
        return goodsCom;
    }

    /*******************************************************************************************************************/

    /**
     *商城页面跳转控制类
     */

    /**
     * 跳转商品详情页面
     * 2022.4.7
     */
    @RequestMapping(value="/product")
    public String product(@RequestParam(name="id")Integer id,Model model){
        GoodsCom goodsCom = productObject(id);
        model.addAttribute("obj",goodsCom);
        return "/product";
    }

    /**
     * 跳转结算页面
     * 2022.4.7
     */
    @RequestMapping(value="/checkout")
    public String checkOut(@RequestParam(name="id")Integer id,@RequestParam(name="number")Integer number,Model model){
        GoodsCom goodsCom = productObject(id);
        model.addAttribute("obj",goodsCom);
        model.addAttribute("number",number);
        return "/checkout";
    }

    /**
     * 购物车跳转结算页面
     * 2022.4.26
     * @param  '购物车选中商品id'
     * 以拼接字符串传递列表格式数据
     */
    @RequestMapping(value="/checkoutCart")
    public String checkOutCart(String cart,Model model){
        model.addAttribute("cart",cart);
        return "/checkoutCart";
    }

    /**
     * 结算页面获取购物车对象
     * @param id
     * @return
     */
    @RequestMapping(value = "/getCartObject")
    @ResponseBody
    public GoodsCom getCartObject(Integer id){
        Customer customer = (Customer) SecurityUtils.getSubject().getPrincipal();
        String number = redisService.getCartOne(customer.getId(),id);
        GoodsCom goodsCom = productObject(id);
        //以信息暂时代传数量
        goodsCom.setInformation(number);
        return goodsCom;
    }

    /**
     * 跳转整机结算页面
     * 2022.5.5
     * 以拼接字符串传递列表格式数据
     */
    @RequestMapping(value="/checkoutCombination")
    public String checkOutCombination(String combination,Model model){
        model.addAttribute("combination",combination);
        return "/checkoutCombination";
    }

    /**
     * 跳转支付页面(弹出窗)
     * model传递付款信息
     */
    @RequestMapping(value = "/payPage")
    public String pay(Integer id,String check,Model model){
        switch (check){
            case "Alipay":
                model.addAttribute("pay","alipay.jpg");
                break;
            case "WeChatPay":
                model.addAttribute("pay","wechatpay.jpg");
                break;
            default:break;
        }
        model.addAttribute("id",id);
        return "/payPage";
    }

    /**
     * 跳转智能装机服务页面
     * 2022.4.18
     * @return combinationForm.html 智能装机算法表单提交页
     */
    @RequestMapping(value = "/combinationForm")
    public String combinationForm(){
        Customer customer = (Customer) SecurityUtils.getSubject().getPrincipal();
        if(customer != null){
            return "/combinationForm";
        }else {
            return "/login";
        }
    }

    /**
     * 跳转个人页面
     */
    @RequestMapping(value = "/customer")
    public String love(String type,Model model){
        Customer customer = (Customer) SecurityUtils.getSubject().getPrincipal();
        if(customer != null){
            model.addAttribute("Page",type);
            return "/customer";
        }else {
            return "/login";
        }
    }


    /*******************************************************************************************************************/
    /**商城功能**/
    /*******************************************************************************************************************/

    /**
     * 获得热销商品
     * @param index
     */

    @RequestMapping(value = "/hotGoodsList")
    @ResponseBody
    public Object hotGoodsList(Integer index){
        List<String> list = redisService.getHotGoodsId(index);
        List<GoodsCom> goodsComList = new ArrayList();
        for (String id : list){
            goodsComList.add(productObject(Integer.parseInt(id)));
        }
        return ResultMapUtil.getHashMapList(goodsComList);
    }

    /**
     * 获得新上架商品
     * @param index
     */

    @RequestMapping(value = "/newGoodsList")
    @ResponseBody
    public Object newGoodsList(Integer index){
        List<String> list = redisService.getNewGoodsId(index);
        List<GoodsCom> goodsComList = new ArrayList();
        for (String id : list){
            goodsComList.add(productObject(Integer.parseInt(id)));
        }
        return ResultMapUtil.getHashMapList(goodsComList);
    }

    /**
     * 获得随机在售商品
     * @param index
     */

    @RequestMapping(value = "/randomGoodsList")
    @ResponseBody
    public Object randomGoodsList(Integer index){
        List<String> list = redisService.getRandomGoodsId(index);
        List<GoodsCom> goodsComList = new ArrayList();
        for (String id : list){
            goodsComList.add(productObject(Integer.parseInt(id)));
        }
        return ResultMapUtil.getHashMapList(goodsComList);
    }

    /**
     * 获取商品(对外)
     */
    @RequestMapping(value = "/getGoods")
    @ResponseBody
    public GoodsCom selectGoods(Integer id){
        GoodsCom goodsCom = productObject(id);
        return goodsCom;
    }


    /*******************************************************************************************************************/
    /**业务操作**/
    /*******************************************************************************************************************/

    /**
     * 业务逻辑类
     * 2022.4.10
     */

    /**
     * 2022.4.10
     * 创建单件订单
     * @param goodsid
     * @param number
     * @param address
     * @return orderId
     */
    @Resource
    private IOrderRetailService OrderRetailService;

    @RequestMapping(value = "/orderRetail")
    @ResponseBody
    public Integer AddOrderRetail(Integer goodsid,Integer number,String address){

        Customer customer = (Customer) SecurityUtils.getSubject().getPrincipal();
        Integer id =redisService.getOrderId();

        GoodsCom goodsCom = productObject(goodsid);
        OrderRetail orderRetail = new OrderRetail();

        orderRetail.setId(id);
        orderRetail.setCustomerId(customer.getId());
        orderRetail.setGoodsId(goodsCom.getId());
        orderRetail.setGoodsName(goodsCom.getName());
        orderRetail.setNumber(number);
        orderRetail.setPrice(goodsCom.getPrice());
        orderRetail.setSumPrice(goodsCom.getPrice().multiply(new BigDecimal(number)));
        orderRetail.setSumSalePrice(goodsCom.getSalePrice().multiply(new BigDecimal(number)));
        orderRetail.setAddress(address);
        orderRetail.setState("未付款");

        try{
            OrderRetailService.addOrderRetail(orderRetail);
            redisService.setOrderCategory(id,goodsCom.getCategory());
            redisService.saleChangeStock(goodsCom.getId(),number);
            redisService.saleChangeSales(goodsCom.getId(),number);
            return id;
        }catch (Exception e){
            System.out.println(e);
            return 0;
        }
    }

    /**
     * 购物车创建多个单件订单
     */
    @RequestMapping(value = "/orderCart")
    @ResponseBody
    public List AddOrderCart(String cart,String address){

        Customer customer = (Customer) SecurityUtils.getSubject().getPrincipal();
        //解析cart为包含商品id的数组
        String[] list = cart.split(",");
        try {
            List<Integer> array = new ArrayList<>();
            for (String id : list){
                //查询购物车中该商品数量
                String number = redisService.getCartOne(customer.getId(),Integer.parseInt(id));
                //生成订单
                array.add(AddOrderRetail(Integer.parseInt(id),Integer.parseInt(number),address));
                //生成订单后从购物车删除商品
                redisService.delCart(customer.getId(),Integer.parseInt(id));
            }
            return array;
        }catch(Exception e){
            System.out.println(e);
            return null;
        }
    }

    /**
     * 2022.4.18
     * 创建整机订单
     * @param   List<Integer> goodsid
     * @return orderId
     */

    @Resource
    private IOrderCombinationService OrderCombinationService;

    @RequestMapping(value = "/orderCombination")
    @ResponseBody
    public Integer AddOrderCombination(String combination,String address){
        Customer customer = (Customer) SecurityUtils.getSubject().getPrincipal();
        Integer id =redisService.getOrderId();
        BigDecimal price =  BigDecimal.ZERO;
        BigDecimal salePrice = BigDecimal.ZERO;
        OrderCombination orderCombination = new OrderCombination();
        String[] list = combination.split(",");
        for(String goodsId : list){
            Integer goodsid = Integer.parseInt(goodsId);
            String category = redisService.getGoodsCategory(goodsid);
            switch (category){
                case "motherboard":
                    Motherboard motherboard = motherboardService.queryMotherboardById(goodsid);
                    orderCombination.setMotherboardId(goodsid);
                    orderCombination.setMotherboardPrice(motherboard.getPrice());
                    orderCombination.setMotherboardSalePrice(motherboard.getSalePrice());
                    price = price.add(motherboard.getPrice());
                    salePrice = salePrice.add(motherboard.getSalePrice());
                    break;
                case "cpu":
                    Cpu cpu = cpuService.queryCpuById(goodsid);
                    orderCombination.setCpuId(goodsid);
                    orderCombination.setCpuPrice(cpu.getPrice());
                    orderCombination.setCpuSalePrice(cpu.getSalePrice());
                    price = price.add(cpu.getPrice());
                    salePrice = salePrice.add(cpu.getSalePrice());
                    break;
                case "graphicscard":
                    Graphicscard graphicscard = graphicscardService.queryGraphicscardById(goodsid);
                    orderCombination.setGcId(goodsid);
                    orderCombination.setGcPrice(graphicscard.getPrice());
                    orderCombination.setGcSalePrice(graphicscard.getSalePrice());
                    price = price.add(graphicscard.getPrice());
                    salePrice = salePrice.add(graphicscard.getSalePrice());
                    break;
                case "ram":
                    Ram ram = ramService.queryRamById(goodsid);
                    orderCombination.setRamId(goodsid);
                    orderCombination.setRamPrice(ram.getPrice());
                    orderCombination.setRamSalePrice(ram.getSalePrice());
                    price = price.add(ram.getPrice());
                    salePrice = salePrice.add(ram.getSalePrice());
                    break;
                case "ssd":
                    Ssd ssd = ssdService.querySsdById(goodsid);
                    orderCombination.setSsdId(goodsid);
                    orderCombination.setSsdPrice(ssd.getPrice());
                    orderCombination.setSsdSalePrice(ssd.getSalePrice());
                    price = price.add(ssd.getPrice());
                    salePrice = salePrice.add(ssd.getSalePrice());
                    break;
                case "heatsink":
                    Heatsink heatsink = heatsinkService.queryHeatsinkById(goodsid);
                    orderCombination.setHeatsinkId(goodsid);
                    orderCombination.setHeatsinkPrice(heatsink.getPrice());
                    orderCombination.setHeatsinkSalePrice(heatsink.getSalePrice());
                    price = price.add(heatsink.getPrice());
                    salePrice = salePrice.add(heatsink.getSalePrice());
                    break;
                case "power":
                    Power power = powerService.queryPowerById(goodsid);
                    orderCombination.setPowerId(goodsid);
                    orderCombination.setPowerPrice(power.getPrice());
                    orderCombination.setPowerSalePrice(power.getSalePrice());
                    price = price.add(power.getPrice());
                    salePrice = salePrice.add(power.getSalePrice());
                    break;
                case "case":
                    Case thecase = caseService.queryCaseById(goodsid);
                    orderCombination.setCaseId(goodsid);
                    orderCombination.setCasePrice(thecase.getPrice());
                    orderCombination.setCaseSalePrice(thecase.getSalePrice());
                    price = price.add(thecase.getPrice());
                    salePrice = salePrice.add(thecase.getSalePrice());
                    break;
                default: break;
            }
        }
        orderCombination.setId(id);
        orderCombination.setCustomerId(customer.getId());
        orderCombination.setSumPrice(price);
        orderCombination.setSumSalePrice(salePrice);
        orderCombination.setAddress(address);
        orderCombination.setState("未付款");

        try {
            OrderCombinationService.addOrderCombination(orderCombination);
            redisService.setOrderCategory(id,"combination");
            redisService.delCombination(customer.getId());
            //修改库存和销量
            redisService.saleChangeStock(orderCombination.getMotherboardId(),1);
            redisService.saleChangeSales(orderCombination.getMotherboardId(),1);
            redisService.saleChangeStock(orderCombination.getCpuId(),1);
            redisService.saleChangeSales(orderCombination.getCpuId(),1);
            redisService.saleChangeStock(orderCombination.getGcId(),1);
            redisService.saleChangeSales(orderCombination.getGcId(),1);
            redisService.saleChangeStock(orderCombination.getRamId(),1);
            redisService.saleChangeSales(orderCombination.getRamId(),1);
            redisService.saleChangeStock(orderCombination.getSsdId(),1);
            redisService.saleChangeSales(orderCombination.getSsdId(),1);
            redisService.saleChangeStock(orderCombination.getHeatsinkId(),1);
            redisService.saleChangeSales(orderCombination.getHeatsinkId(),1);
            redisService.saleChangeStock(orderCombination.getPowerId(),1);
            redisService.saleChangeSales(orderCombination.getPowerId(),1);
            redisService.saleChangeStock(orderCombination.getCaseId(),1);
            redisService.saleChangeSales(orderCombination.getCaseId(),1);
            return id;
        }catch (Exception e){
            return 0;
        }
    }

    /**
     * 付款
     * @param id
     */
    @RequestMapping(value = "/pay")
    @ResponseBody
    public Object pay(Integer id){
        try{
            String category = redisService.getOrderCategory(id);
            if ("combination".equals(category)){
                OrderCombinationService.payOrderCombinationById(id);
            }else {
                OrderRetailService.payOrderRetailById(id);
            }
            return ResultMapUtil.getHashMapSave(1);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 取消订单
     * @param id
     */
    @RequestMapping(value = "/cancel")
    @ResponseBody
    public Object cancel(Integer id){
        try{
            String category = redisService.getOrderCategory(id);
            if ("combination".equals(category)){
                OrderCombinationService.cancelOrderCombinationById(id);

            }else {
                OrderRetailService.cancelOrderRetailById(id);
                //取消成功后还原库存
                redisService.saleChangeStock((OrderRetailService.queryOrderRetailById(id)).getGoodsId(),-(OrderRetailService.queryOrderRetailById(id)).getNumber());
                redisService.saleChangeSales((OrderRetailService.queryOrderRetailById(id)).getGoodsId(),-(OrderRetailService.queryOrderRetailById(id)).getNumber());
            }
            return ResultMapUtil.getHashMapSave(1);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /*******************************************************************************************************************/
    /**商品类操作**/
    /*******************************************************************************************************************/

    /**
     * 主板类
     * 2022.3.27
     */

    @Resource
    private IMotherboardService motherboardService;

    /**
     * 获取商品信息
     */
    @RequestMapping(value="/motherboardQueryById")
    @ResponseBody
    public Object motherboardQueryById(@RequestParam(name="id")Integer id){
        Motherboard motherboard = motherboardService.queryMotherboardById(id);
        return motherboard;
    }


    /**
     * 分页查询列表
     * 非返回页面
     */
    @RequestMapping(value = "/motherboardQueryPage")
    @ResponseBody
    public Object motherboardQueryPage(@RequestParam(defaultValue = "1")int page,@RequestParam(defaultValue = "10")int limit,String param){
        try {
            IPage<Motherboard> iPage = motherboardService.selectMotherboardPage(page,limit,param);
            return ResultMapUtil.getHashMapMysqlPage(iPage);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 获取所有信息并返回列表格式
     */
    @RequestMapping(value = "/motherboardList")
    @ResponseBody
    public Object motherboardList(){
        List<Motherboard> motherboardList = motherboardService.queryMotherboardList();
        return ResultMapUtil.getHashMapList(motherboardList);
    }

    /*******************************************************************************************************************/

    /**
     * 芯片类
     * 2022.3.28
     */
    @Resource
    private ICpuService cpuService;


    /**
     * 获取商品信息
     */
    @RequestMapping(value="/cpuQueryById")
    @ResponseBody
    public Object cpuQueryById(@RequestParam(name="id")Integer id){
        Cpu cpu = cpuService.queryCpuById(id);
        return cpu;
    }

    /**
     * 分页查询列表
     * 非返回页面
     */
    @RequestMapping(value = "/cpuQueryPage")
    @ResponseBody
    public Object cpuQueryPage(@RequestParam(defaultValue = "1")int page,@RequestParam(defaultValue = "10")int limit,String param){
        try {
            IPage<Cpu> iPage = cpuService.selectCpuPage(page,limit,param);
            return ResultMapUtil.getHashMapMysqlPage(iPage);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 获取所有信息并返回列表格式
     */
    @RequestMapping(value = "/cpuList")
    @ResponseBody
    public Object cpuList(){
        List<Cpu> cpuList = cpuService.queryCpuList();
        return ResultMapUtil.getHashMapList(cpuList);
    }

    /*******************************************************************************************************************/

    /**
     * 显卡类
     * 2022.3.28
     */
    @Resource
    private IGraphicscardService graphicscardService;

    /**
     * 获取商品信息
     */
    @RequestMapping(value="/graphicscardQueryById")
    @ResponseBody
    public Object graphicscardQueryById(@RequestParam(name="id")Integer id){
        Graphicscard graphicscard = graphicscardService.queryGraphicscardById(id);
        return graphicscard;
    }

    /**
     * 分页查询列表
     * 非返回页面
     */
    @RequestMapping(value = "/graphicscardQueryPage")
    @ResponseBody
    public Object graphicscarduQueryPage(@RequestParam(defaultValue = "1")int page,@RequestParam(defaultValue = "10")int limit,String param){
        try {
            IPage<Graphicscard> iPage = graphicscardService.selectGraphicscardPage(page,limit,param);
            return ResultMapUtil.getHashMapMysqlPage(iPage);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 获取所有信息并返回列表格式
     */
    @RequestMapping(value = "/graphicscardList")
    @ResponseBody
    public Object graphicscardList(){
        List<Graphicscard> graphicscardList = graphicscardService.queryGraphicscardList();
        return ResultMapUtil.getHashMapList(graphicscardList);
    }

    /*******************************************************************************************************************/

    /**
     * 内存类
     * 2022.4.5
     */
    @Resource
    private IRamService ramService;

    /**
     * 获取商品信息
     */
    @RequestMapping(value="/ramQueryById")
    @ResponseBody
    public Object ramQueryById(@RequestParam(name="id")Integer id){
        Ram ram = ramService.queryRamById(id);
        return ram;
    }

    /**
     * 分页查询列表
     * 非返回页面
     */
    @RequestMapping(value = "/ramQueryPage")
    @ResponseBody
    public Object ramQueryPage(@RequestParam(defaultValue = "1")int page,@RequestParam(defaultValue = "10")int limit,String param){
        try {
            IPage<Ram> iPage = ramService.selectRamPage(page,limit,param);
            return ResultMapUtil.getHashMapMysqlPage(iPage);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 获取所有信息并返回列表格式
     */
    @RequestMapping(value = "/ramList")
    @ResponseBody
    public Object ramList(){
        List<Ram> ramList = ramService.queryRamList();
        return ResultMapUtil.getHashMapList(ramList);
    }

    /*******************************************************************************************************************/

    /**
     * 固态硬盘类
     * 2022.3.28
     */
    @Resource
    private ISsdService ssdService;

    /**
     * 获取商品信息
     */
    @RequestMapping(value="/ssdQueryById")
    @ResponseBody
    public Object ssdQueryById(@RequestParam(name="id")Integer id){
        Ssd ssd = ssdService.querySsdById(id);
        return ssd;
    }

    /**
     * 分页查询列表
     * 非返回页面
     */
    @RequestMapping(value = "/ssdQueryPage")
    @ResponseBody
    public Object ssdQueryPage(@RequestParam(defaultValue = "1")int page,@RequestParam(defaultValue = "10")int limit,String param){
        try {
            IPage<Ssd> iPage = ssdService.selectSsdPage(page,limit,param);
            return ResultMapUtil.getHashMapMysqlPage(iPage);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 获取所有信息并返回列表格式
     */
    @RequestMapping(value = "/ssdList")
    @ResponseBody
    public Object ssdList(){
        List<Ssd> ssdList = ssdService.querySsdList();
        return ResultMapUtil.getHashMapList(ssdList);
    }

    /*******************************************************************************************************************/

    /**
     * 散热器类
     * 2022.4.3
     */
    @Resource
    private IHeatsinkService heatsinkService;

    /**
     * 获取商品信息
     */
    @RequestMapping(value="/heatsinkQueryById")
    @ResponseBody
    public Object heatsinkQueryById(@RequestParam(name="id")Integer id){
        Heatsink heatsink = heatsinkService.queryHeatsinkById(id);
        return heatsink;
    }

    /**
     * 分页查询列表
     * 非返回页面
     */
    @RequestMapping(value = "/heatsinkQueryPage")
    @ResponseBody
    public Object heatsinkQueryPage(@RequestParam(defaultValue = "1")int page,@RequestParam(defaultValue = "10")int limit,String param){
        try {
            IPage<Heatsink> iPage = heatsinkService.selectHeatsinkPage(page,limit,param);
            return ResultMapUtil.getHashMapMysqlPage(iPage);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 获取所有信息并返回列表格式
     */
    @RequestMapping(value = "/heatsinkList")
    @ResponseBody
    public Object heatsinkList(){
        List<Heatsink> heatsinkList = heatsinkService.queryHeatsinkList();
        return ResultMapUtil.getHashMapList(heatsinkList);
    }

    /*******************************************************************************************************************/

    /**
     * 电源类
     * 2022.3.28
     */
    @Resource
    private IPowerService powerService;

    /**
     * 获取商品信息
     */
    @RequestMapping(value="/powerQueryById")
    @ResponseBody
    public Object powerQueryById(@RequestParam(name="id")Integer id){
        Power power = powerService.queryPowerById(id);
        return power;
    }

    /**
     * 分页查询列表
     * 非返回页面
     */
    @RequestMapping(value = "/powerQueryPage")
    @ResponseBody
    public Object powerQueryPage(@RequestParam(defaultValue = "1")int page,@RequestParam(defaultValue = "10")int limit,String param){
        try {
            IPage<Power> iPage = powerService.selectPowerPage(page,limit,param);
            return ResultMapUtil.getHashMapMysqlPage(iPage);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 获取所有信息并返回列表格式
     */
    @RequestMapping(value = "/powerList")
    @ResponseBody
    public Object powerList(){
        List<Power> powerList = powerService.queryPowerList();
        return ResultMapUtil.getHashMapList(powerList);
    }

    /*******************************************************************************************************************/

    /**
     * 机箱类
     * 2022.3.28
     */
    @Resource
    private ICaseService caseService;

    /**
     * 获取商品信息
     */
    @RequestMapping(value="/caseQueryById")
    @ResponseBody
    public Object caseQueryById(@RequestParam(name="id")Integer id){
        Case thecase = caseService.queryCaseById(id);
        return thecase;
    }

    /**
     * 分页查询列表
     * 非返回页面
     */
    @RequestMapping(value = "/caseQueryPage")
    @ResponseBody
    public Object caseQueryPage(@RequestParam(defaultValue = "1")int page,@RequestParam(defaultValue = "10")int limit,String param){
        try {
            IPage<Case> iPage = caseService.selectCasePage(page,limit,param);
            return ResultMapUtil.getHashMapMysqlPage(iPage);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 获取所有信息并返回列表格式
     */
    @RequestMapping(value = "/caseList")
    @ResponseBody
    public Object caseList(){
        List<Case> caseList = caseService.queryCaseList();
        return ResultMapUtil.getHashMapList(caseList);
    }
}

