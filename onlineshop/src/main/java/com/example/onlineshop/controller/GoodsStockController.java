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
@RequestMapping(value = "/goodsStock")//对应前端ajax的url，是一个接口，也和index中的lay-href相同

/**
 * @author wu
 * 2022.4.23
 * 商品库存信息控制类
 */
public class GoodsStockController {

    @RequestMapping
    public String goodsSale(){
        Set<String> set = redisService.getAllRole(((Worker) SecurityUtils.getSubject().getPrincipal()).getJob());
        if(set.contains("goodsStock")){
            return "/goodsStock";
        }
        else {
            return "/stop";
        }
    }

    /**
     * redis通用操作
     */

    @Resource
    private RedisService redisService;

    /**
     * 获取商品库存
     */
    @RequestMapping(value = "/getNumber")
    @ResponseBody
    public Integer getGoodsStock(Integer id){
        return redisService.getStockById(id);
    }

    /**
     * 修改商品库存
     */


    /*******************************************************************************************************************/


    /**
     * 主板类
     * 2022.4.23
     */

    @Resource
    private IMotherboardService motherboardService;


    /**
     * 转向修改页面
     */
    @RequestMapping(value="/motherboardQueryById")
    public String motherboardQueryById(@RequestParam(name="id")Integer id, Model model){//两个id分别指什么?
        Motherboard motherboard = motherboardService.queryMotherboardById(id);
        //model.addAttribute("obj",value)，往前台“obj”传数据value，可以是对象
        model.addAttribute("obj",motherboard);
        return "/stockComPage";
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
     * 2022.4.23
     */
    @Resource
    private ICpuService cpuService;

    /**
     * 转向修改页面
     */
    @RequestMapping(value="/cpuQueryById")
    public String cpuQueryById(@RequestParam(name="id")Integer id, Model model){
        Cpu cpu = cpuService.queryCpuById(id);
        model.addAttribute("obj",cpu);
        return "/stockComPage";
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
     * 2022.4.23
     */
    @Resource
    private IGraphicscardService graphicscardService;

    /**
     * 转向修改页面
     */
    @RequestMapping(value="/graphicscardQueryById")
    public String graphicscardQueryById(@RequestParam(name="id")Integer id, Model model){
        Graphicscard graphicscard = graphicscardService.queryGraphicscardById(id);
        model.addAttribute("obj",graphicscard);
        return "/stockComPage";
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
     * 2022.4.23
     */
    @Resource
    private IRamService ramService;

    /**
     * 转向修改页面
     */
    @RequestMapping(value="/ramQueryById")
    public String ramQueryById(@RequestParam(name="id")Integer id, Model model){
        Ram ram = ramService.queryRamById(id);
        model.addAttribute("obj",ram);
        return "/stockComPage";
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
     * 2022.4.23
     */
    @Resource
    private ISsdService ssdService;

    /**
     * 转向修改页面
     */
    @RequestMapping(value="/ssdQueryById")
    public String ssdQueryById(@RequestParam(name="id")Integer id, Model model){
        Ssd ssd = ssdService.querySsdById(id);
        model.addAttribute("obj",ssd);
        return "/stockComPage";
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
     * 2022.4.23
     */
    @Resource
    private IHeatsinkService heatsinkService;

    /**
     * 转向修改页面
     */
    @RequestMapping(value="/heatsinkQueryById")
    public String heatsinkQueryById(@RequestParam(name="id")Integer id, Model model){
        Heatsink heatsink = heatsinkService.queryHeatsinkById(id);
        model.addAttribute("obj",heatsink);
        return "/stockComPage";
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
     * 2022.4.23
     */
    @Resource
    private IPowerService powerService;

    /**
     * 转向修改页面
     */
    @RequestMapping(value="/powerQueryById")
    public String powerQueryById(@RequestParam(name="id")Integer id, Model model){
        Power power = powerService.queryPowerById(id);
        model.addAttribute("obj",power);
        return "/stockComPage";
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
     * 2022.4.23
     */
    @Resource
    private ICaseService caseService;

    /**
     * 转向修改页面
     */
    @RequestMapping(value="/caseQueryById")
    public String caseQueryById(@RequestParam(name="id")Integer id, Model model){
        Case thecase = caseService.queryCaseById(id);
        model.addAttribute("obj",thecase);
        return "/stockComPage";
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

