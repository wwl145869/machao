package com.dci.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.RedisConnection;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;
/*
*
* by ：wanliw
* ver：0.1
* */

@Component
public class RedisUtil {
    @Autowired
    @Qualifier(value = "redisTemplate")
    RedisTemplate redisTemplate;


    public String select(int i){
        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
        String res=null;
        try {
            connection.select(i);
            res="->以切换为<"+i+">库";
        } catch (Exception e) {
            e.printStackTrace();
            res =e.getLocalizedMessage();
        }
        return res;

    }

    public  String falshAll(){
        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
        String res=null;
        try {
            connection.flushAll();
            res="------清除全部库key成功------";
        } catch (Exception e) {
            e.printStackTrace();
            res =e.getLocalizedMessage();
        }
        return  res;
    }
    public String falshDb() {
        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
        String res=null;
        try {
            connection.flushDb();
            res="------清除当前库key成功------";
        } catch (Exception e) {
            e.printStackTrace();
            res =e.getLocalizedMessage();
     }
        return  res;
}
    public void set(String key,Object val){

         redisTemplate.opsForValue().set(key, val);

    }
    public void setIfTimeout(String key,Object val,int i){
        redisTemplate.opsForValue().setIfAbsent(key,val);
        redisTemplate.opsForValue().set(key,val,i,TimeUnit.SECONDS);

    }
    public Object get(String key){
        Object o = redisTemplate.opsForValue().get(key);
        return o;
    }
    public void sadd(String key,Object val){
        redisTemplate.opsForSet().add(key,val);
    }
    public Boolean sIsMember(String key,Object val){
        Boolean member = redisTemplate.opsForSet().isMember(key, val);
        return member;
    }
    public Boolean exists(String key){
    return   redisTemplate.hasKey(key);
    }

    public void  expire(){

    }
    public void setDltL(){
        redisTemplate.opsForSet().add("dltL",1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35);
    }

    public List getDltL(){
    return  redisTemplate.opsForSet().randomMembers("dltL", 5);
    }
    public void   setDltR(){
        redisTemplate.opsForSet().add("dltR",1,2,3,4,5,6,7,8,9,10,11,12);
    }
    public List  getDltR(){
        return  redisTemplate.opsForSet().randomMembers("dltR", 2);
    }

}
