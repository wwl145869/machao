package com.dci.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dci.pojo.DietRecords;
import com.dci.pojo.Msg;
import com.dci.pojo.Session;
import com.dci.pojo.User;
import com.dci.service.DietRecordsInterface;
import com.dci.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.UUID;

/*
* 上传饮食记录
* */
@Controller
@ResponseBody
public class DietRecordsController {
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    DietRecordsInterface dietRecordsInterface;
    //3、记录接口
    @PostMapping("DietRecords/writeRecords.do")
    public Msg writeRecords(DietRecords dietRecords,Session session){//可以用session来拿到用户信息和令牌，
        String id = UUID.randomUUID().toString();
        dietRecords.setId(id);
        String tokenache= (String)redisUtil.get("token:" + session.getUsername());
        Boolean exists = redisUtil.exists("user:" + session.getUsername());
        return chuli(tokenache, exists, session, dietRecords);//具体处理方法;
    }
    //具体处理方法
    public Msg chuli(String tokenache,Boolean exists,Session session,DietRecords dietRecords){
        if(exists){
            if(tokenache.equals(session.getToken())){
                //用户存在,令牌吻合，
                int ret = dietRecordsInterface.write(dietRecords);
                if(ret==0){
                    return  new Msg("饮食记录写入成功", 200,session.getToken());
                }else{
                    return  new Msg("饮食记录写入失败", 500, "请检查必填参数是否填写完整",session.getToken());
                }
            } else{
                //令牌有误
                return  new Msg("令牌有误", 501, "请仔细检查您传入的令牌",session.getToken());
            }
        }else{
            //未登录就直接操作
            return  new Msg("未登录无法操作", 502, "不可以跳过登录，请注意您的操作是否规范","");
        }
    }
    @PostMapping("DietRecords/queryAll.do")
    public Msg queryAll(Session session){
        String tokenache= (String)redisUtil.get("token:" + session.getUsername());
        Boolean exists = redisUtil.exists("user:" + session.getUsername());
        return chuli(exists,tokenache,session);
    }
    public Msg chuli( Boolean exists, String tokenache, Session session){
        if(exists){
            if(tokenache.equals(session.getToken())){
                //用户存在,令牌吻合
                List<DietRecords> list = dietRecordsInterface.queryAll();
                String jsons = JSONObject.toJSONString(list);
                return  new Msg("查询成功", 200,jsons);
            } else{
                //令牌有误
                return  new Msg("令牌有误", 501, "请仔细检查您传入的令牌",session.getToken());
            }
        }else{
            //未登录就直接操作
            return  new Msg("未登录无法操作", 502, "不可以跳过登录，请注意您的操作是否规范","");
        }
    }
}
