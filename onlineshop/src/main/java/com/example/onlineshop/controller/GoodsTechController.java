package com.example.onlineshop.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.onlineshop.common.ResultMapUtil;
import com.example.onlineshop.entity.*;
import com.example.onlineshop.service.*;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping(value = "/goodsTech")//对应前端ajax的url，是一个接口，也和index中的lay-href相同

/**
 * @author wu
 * 商品技术信息控制类
 */
public class GoodsTechController {

    @RequestMapping
    public String goodsTech(){
        Set<String> set = redisService.getAllRole(((Worker) SecurityUtils.getSubject().getPrincipal()).getJob());
        if(set.contains("goodsTech")){
            return "/goodsTech";
        }
        else {
            return "/stop";
        }
    }

    /**
     * redis通用操作
     * 2022.4.1
     */

    @Resource
    private RedisService redisService;

    /**
     * 新增商品时查询id
     */
    @RequestMapping(value = "/getGoodsId")
    @ResponseBody
    public Object GoodsId(){
        Integer id = redisService.getGoodsId();
        return id;
    }

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


    /**
     * 主板类
     * 2022.3.27
     */

    @Resource
    private IMotherboardService motherboardService;

    /**
     * 转向新增页面
     */
    @RequestMapping(value="/motherboardPage")
    public String motherboardPager(){
        return "/motherboardPage";
    }

    /**
     * 新增商品数据并在redis写入商品分类
     */
    @RequestMapping(value = "/motherboardAdd")
    @ResponseBody
    public Object motherboardAdd(Motherboard motherboard){
        try{
            int i=motherboardService.addMotherboard(motherboard);
            redisService.setGoodsDefault(motherboard.getId(),"motherboard");
            return ResultMapUtil.getHashMapSave(i);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 逻辑删除数据
     */
    @RequestMapping(value = "/motherboardDelById")
    @ResponseBody
    public Object motherboardDelById(Integer board_id){
        try{
            int i = motherboardService.lodelMotherboardById(board_id);
            return ResultMapUtil.getHashMapSave(i);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 转向修改页面
     */
    @RequestMapping(value="/motherboardQueryById")
    public String motherboardQueryById(@RequestParam(name="id")Integer id, Model model){//两个id分别指什么?
        Motherboard motherboard = motherboardService.queryMotherboardById(id);
        model.addAttribute("obj",motherboard);
        return "/motherboardPage";
    }

    /**
     * 修改数据
     */
    @RequestMapping(value = "/motherboardEdit")
    @ResponseBody
    public Object motherboardEdit(Motherboard motherboard){
        try{
            int i = motherboardService.editMotherboard(motherboard);
            return ResultMapUtil.getHashMapSave(i);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 错误日志
     * @author wu
     * 2022.4.3
     * 错误描述：分页功能失效
     * 查询layui官方文档后发现开启自动分页后，request里会自动带上分页数据变量: (page：第几页;limit：每页几条数据)
     * 注解@RequestParam 用于将请求参数区数据映射到功能处理方法的参数上，使用时要求定义的形参与前端传输形参同名才能建立映射
     * 也可以在前端layui表头参数中自定义
     * * request: {
     *        pageName: 'pageNo', // page
     *        limitName: 'pageSize' // limit
     * * },
     */

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
     * 转向新增页面
     */
    @RequestMapping(value="/cpuPage")
    public String cpuPager(){
        return "/cpuPage";
    }

    /**
     * 新增数据
     */
    @RequestMapping(value = "/cpuAdd")
    @ResponseBody
    public Object cpuAdd(Cpu cpu){
        try{
            int i=cpuService.addCpu(cpu);
            redisService.setGoodsDefault(cpu.getId(),"cpu");
            return ResultMapUtil.getHashMapSave(i);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 逻辑删除数据
     */
    @RequestMapping(value = "/cpuDelById")
    @ResponseBody
    public Object cpuDelById(Integer cpu_id){
        try{
            int i = cpuService.lodelCpuById(cpu_id);
            return ResultMapUtil.getHashMapSave(i);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 转向修改页面
     */
    @RequestMapping(value="/cpuQueryById")
    public String cpuQueryById(@RequestParam(name="id")Integer id, Model model){
        Cpu cpu = cpuService.queryCpuById(id);
        model.addAttribute("obj",cpu);
        return "/cpuPage";
    }

    /**
     * 修改数据
     */
    @RequestMapping(value = "/cpuEdit")
    @ResponseBody
    public Object cpuEdit(Cpu cpu){
        try{
            int i = cpuService.editCpu(cpu);
            return ResultMapUtil.getHashMapSave(i);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
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
     * 转向新增页面
     */
    @RequestMapping(value="/graphicscardPage")
    public String graphicscardPager(){
        return "/graphicscardPage";
    }

    /**
     * 新增数据
     */
    @RequestMapping(value = "/graphicscardAdd")
    @ResponseBody
    public Object graphicscardAdd(Graphicscard graphicscard){
        try{
            int i=graphicscardService.addGraphicscard(graphicscard);
            redisService.setGoodsDefault(graphicscard.getId(),"graphicscard");
            return ResultMapUtil.getHashMapSave(i);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 逻辑删除数据
     */
    @RequestMapping(value = "/graphicscardDelById")
    @ResponseBody
    public Object graphicscardDelById(Integer graphicscard_id){
        try{
            int i = graphicscardService.lodelGraphicscardById(graphicscard_id);
            return ResultMapUtil.getHashMapSave(i);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 转向修改页面
     */
    @RequestMapping(value="/graphicscardQueryById")
    public String graphicscardQueryById(@RequestParam(name="id")Integer id, Model model){
        Graphicscard graphicscard = graphicscardService.queryGraphicscardById(id);
        model.addAttribute("obj",graphicscard);
        return "/graphicscardPage";
    }

    /**
     * 修改数据
     */
    @RequestMapping(value = "/graphicscardEdit")
    @ResponseBody
    public Object graphicscardEdit(Graphicscard graphicscard){
        try{
            int i = graphicscardService.editGraphicscard(graphicscard);
            return ResultMapUtil.getHashMapSave(i);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
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
     * 转向新增页面
     */
    @RequestMapping(value="/ramPage")
    public String ramPager(){
        return "/ramPage";
    }

    /**
     * 新增数据
     */
    @RequestMapping(value = "/ramAdd")
    @ResponseBody
    public Object ramAdd(Ram ram){
        try{
            int i=ramService.addRam(ram);
            redisService.setGoodsDefault(ram.getId(),"ram");
            return ResultMapUtil.getHashMapSave(i);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 逻辑删除数据
     */
    @RequestMapping(value = "/ramDelById")
    @ResponseBody
    public Object ramDelById(Integer ram_id){
        try{
            int i = ramService.lodelRamById(ram_id);
            return ResultMapUtil.getHashMapSave(i);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 转向修改页面
     */
    @RequestMapping(value="/ramQueryById")
    public String ramQueryById(@RequestParam(name="id")Integer id, Model model){
        Ram ram = ramService.queryRamById(id);
        model.addAttribute("obj",ram);
        return "/ramPage";
    }

    /**
     * 修改数据
     */
    @RequestMapping(value = "/ramEdit")
    @ResponseBody
    public Object ramEdit(Ram ram){
        try{
            int i = ramService.editRam(ram);
            return ResultMapUtil.getHashMapSave(i);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
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
     * 转向新增页面
     */
    @RequestMapping(value="/ssdPage")
    public String ssdPager(){
        return "/ssdPage";
    }

    /**
     * 新增数据
     */
    @RequestMapping(value = "/ssdAdd")
    @ResponseBody
    public Object ssdAdd(Ssd ssd){
        try{
            int i=ssdService.addSsd(ssd);
            redisService.setGoodsDefault(ssd.getId(),"ssd");
            return ResultMapUtil.getHashMapSave(i);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 逻辑删除数据
     */
    @RequestMapping(value = "/ssdDelById")
    @ResponseBody
    public Object ssdDelById(Integer ssd_id){
        try{
            int i = ssdService.lodelSsdById(ssd_id);
            return ResultMapUtil.getHashMapSave(i);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 转向修改页面
     */
    @RequestMapping(value="/ssdQueryById")
    public String ssdQueryById(@RequestParam(name="id")Integer id, Model model){
        Ssd ssd = ssdService.querySsdById(id);
        model.addAttribute("obj",ssd);
        return "/ssdPage";
    }

    /**
     * 修改数据
     */
    @RequestMapping(value = "/ssdEdit")
    @ResponseBody
    public Object ssdEdit(Ssd ssd){
        try{
            int i = ssdService.editSsd(ssd);
            return ResultMapUtil.getHashMapSave(i);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
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
     * 转向新增页面
     */
    @RequestMapping(value="/heatsinkPage")
    public String heatsinkPager(){
        return "/heatsinkPage";
    }

    /**
     * 新增数据
     */
    @RequestMapping(value = "/heatsinkAdd")
    @ResponseBody
    public Object heatsinkAdd(Heatsink heatsink){
        try{
            int i=heatsinkService.addHeatsink(heatsink);
            redisService.setGoodsDefault(heatsink.getId(),"heatsink");
            return ResultMapUtil.getHashMapSave(i);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 逻辑删除数据
     */
    @RequestMapping(value = "/heatsinkDelById")
    @ResponseBody
    public Object heatsinkDelById(Integer heatsink_id){
        try{
            int i = heatsinkService.lodelHeatsinkById(heatsink_id);
            return ResultMapUtil.getHashMapSave(i);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 转向修改页面
     */
    @RequestMapping(value="/heatsinkQueryById")
    public String heatsinkQueryById(@RequestParam(name="id")Integer id, Model model){
        Heatsink heatsink = heatsinkService.queryHeatsinkById(id);
        model.addAttribute("obj",heatsink);
        return "/heatsinkPage";
    }

    /**
     * 修改数据
     */
    @RequestMapping(value = "/heatsinkEdit")
    @ResponseBody
    public Object heatsinkEdit(Heatsink heatsink){
        try{
            int i = heatsinkService.editHeatsink(heatsink);
            return ResultMapUtil.getHashMapSave(i);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
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
     * 转向新增页面
     */
    @RequestMapping(value="/powerPage")
    public String powerPager(){
        return "/powerPage";
    }

    /**
     * 新增数据
     */
    @RequestMapping(value = "/powerAdd")
    @ResponseBody
    public Object powerAdd(Power power){
        try{
            int i=powerService.addPower(power);
            redisService.setGoodsDefault(power.getId(),"power");
            return ResultMapUtil.getHashMapSave(i);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 逻辑删除数据
     */
    @RequestMapping(value = "/powerDelById")
    @ResponseBody
    public Object powerDelById(Integer power_id){
        try{
            int i = powerService.lodelPowerById(power_id);
            return ResultMapUtil.getHashMapSave(i);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 转向修改页面
     */
    @RequestMapping(value="/powerQueryById")
    public String powerQueryById(@RequestParam(name="id")Integer id, Model model){
        Power power = powerService.queryPowerById(id);
        model.addAttribute("obj",power);
        return "/powerPage";
    }

    /**
     * 修改数据
     */
    @RequestMapping(value = "/powerEdit")
    @ResponseBody
    public Object powerEdit(Power power){
        try{
            int i = powerService.editPower(power);
            return ResultMapUtil.getHashMapSave(i);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
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
     * 转向新增页面
     */
    @RequestMapping(value="/casePage")
    public String casePager(){
        return "/casePage";
    }

    /**
     * 新增数据
     */
    @RequestMapping(value = "/caseAdd")
    @ResponseBody
    public Object caseAdd(Case thecase){
        try{
            int i=caseService.addCase(thecase);
            redisService.setGoodsDefault(thecase.getId(),"case");
            return ResultMapUtil.getHashMapSave(i);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 逻辑删除数据
     */
    @RequestMapping(value = "/caseDelById")
    @ResponseBody
    public Object caseDelById(Integer case_id){
        try{
            int i = caseService.lodelCaseById(case_id);
            return ResultMapUtil.getHashMapSave(i);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 转向修改页面
     */
    @RequestMapping(value="/caseQueryById")
    public String caseQueryById(@RequestParam(name="id")Integer id, Model model){
        Case thecase = caseService.queryCaseById(id);
        model.addAttribute("obj",thecase);
        return "/casePage";
    }

    /**
     * 修改数据
     */
    @RequestMapping(value = "/caseEdit")
    @ResponseBody
    public Object caseEdit(Case thecase){
        try{
            int i = caseService.editCase(thecase);
            return ResultMapUtil.getHashMapSave(i);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
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

