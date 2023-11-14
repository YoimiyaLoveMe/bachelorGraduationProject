package com.example.onlineshop.common;

/**
 * 给前端返回的json格式数据
 */

import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.HashMap;
import java.util.List;

/**
 * @author 吴轶硕
 * 封装通用方法
 */
public class ResultMapUtil {

    /**
     * 登录页面提交信息
     */
    public static HashMap<String,Object> getHashMapLogin(String msg,String code){
        HashMap<String,Object> resultMap = new HashMap<>();
        resultMap.put("msg",msg);
        resultMap.put("code",code);
        /**
         * 魔法值：controller返回的错误码
         * 0:非法用户名
         * 1:登录或注册成功
         * 2:登录密码错误
         * 3:登录-用户不存在
         * 4:注册-用户已存在
         */
        if("1".equals(code)){
            resultMap.put("icon",1);
        }
        else if("0".equals(code)){
            resultMap.put("icon",0);
        }
        else if("2".equals(code)){
            resultMap.put("icon",2);
        }
        else if("3".equals(code)){
            resultMap.put("icon",3);
        }
        else if("4".equals(code)){
            resultMap.put("icon",4);
        }
        resultMap.put("anim",4);
        return resultMap;
    }

    /**
     * 分页查询方法
     */
    public static HashMap<String,Object> getHashMapMysqlPage(IPage<?> object){
        HashMap<String,Object> resultMap = new HashMap<>();
        resultMap.put("code",0);
        resultMap.put("msg","");
        resultMap.put("count",object.getTotal());
        resultMap.put("data",object.getRecords());
        return resultMap;
    }

    /**
     * 异常数据统一处理
     */
    public static HashMap<String,Object> getHashMapException(Exception e) {
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("code", 0);
        resultMap.put("msg", e.getMessage());
        return resultMap;
    }

    /**
     * 保存成功后统一返回
     */
    public static HashMap<String,Object> getHashMapSave(int i){
        HashMap<String,Object> resultMap = new HashMap<>();
        if (i==0){
            resultMap.put("msg","保存失败");
            resultMap.put("code",1);
            resultMap.put("icon",2);
            resultMap.put("anim",6);
        }else{
            resultMap.put("msg","成功");
            resultMap.put("code",0);
            resultMap.put("icon",1);
            resultMap.put("anim",4);
        }
        return resultMap;
    }


    /**
     * List返回格式
     */
    public static HashMap<String,Object> getHashMapList(List<?> list){
        HashMap<String,Object> resultMap = new HashMap<>();
        resultMap.put("code",0);
        if(list!=null&&list.size()>0){
            resultMap.put("msg","");
        }else{
            resultMap.put("msg","无数据！");
        }
        resultMap.put("data",list);
        return resultMap;
    }

    /**
     * 顾客角色权限管理
     * 登录后才可使用购买等功能
     */
    public static HashMap<String,Object> getHashMapRole(int i){
        HashMap<String,Object> resultMap = new HashMap<>();
        if (i==0){
            resultMap.put("msg","请先登录!");
            resultMap.put("code",1);
            resultMap.put("icon",4);
            resultMap.put("anim",6);
        }else{
            resultMap.put("msg","跳转中……");
            resultMap.put("code",0);
            resultMap.put("icon",6);
            resultMap.put("anim",4);
        }
        return resultMap;
    }

    /**
     * 工作人员权限管理
     * redis查询权限码
     * 1: 有权限正常跳转页面
     * 0: 无权限跳转访问禁止页面
     */
}
