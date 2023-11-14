package com.example.onlineshop.controller;

import com.example.onlineshop.common.ResultMapUtil;
import com.example.onlineshop.entity.AIAC;
import com.example.onlineshop.entity.Worker;
import com.example.onlineshop.service.RedisService;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Set;

/**
 * 算法相关controller
 * @author wu
 * 2022.4.27
 */
@Controller
@RequestMapping(value = "/AIACPage")
public class AlgorithmController {

    /**
     * redis
     */
    @Resource
    private RedisService redisService;
    /**
     * AIAC
     * 智能装机算法修改页面
     */
    @RequestMapping
    public String AIACPage(Model model){

        Set<String> set = redisService.getAllRole(((Worker) SecurityUtils.getSubject().getPrincipal()).getJob());
        if(set.contains("aiac")){
            AIAC aiac = new AIAC();
            aiac.setIteration(Integer.parseInt(redisService.getIteration()));
            aiac.setEnvironmentalCapacity(Integer.parseInt(redisService.getEnvironmentalCapacity()));
            aiac.setCrossover(redisService.getCrossover());
            aiac.setMutation(redisService.getMutation());
            aiac.setTechLimit(redisService.getTechLimit());
            aiac.setPriceRatio(redisService.getPriceRatio());
            aiac.setPriceULimit(redisService.getPriceULimit());
            aiac.setPriceDLimit(redisService.getPriceDLimit());
            model.addAttribute("obj",aiac);
            return "/AIACPage";
        }
        else {
            return "/stop";
        }
    }

    /**
     * Iteration 迭代次数
     * environmentalCapacity 环境容纳量
     * Crossover 交叉率
     * Mutation 变异率
     * techLimit 兼容性错误衰减率
     * priceLimit 价格偏移衰减率
     * @return
     */
    @RequestMapping(value = "/editAIAC")
    @ResponseBody
    public Object editAIAC(AIAC aiac){
        try{
            redisService.setBasicParameters(aiac.getIteration(),aiac.getEnvironmentalCapacity(),aiac.getCrossover(),aiac.getMutation(), aiac.getTechLimit(), aiac.getPriceRatio(), aiac.getPriceULimit(),aiac.getPriceDLimit());
            return ResultMapUtil.getHashMapSave(1);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }
}
