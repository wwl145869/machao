package com.dci.service;

import com.dci.mapper.UserMapper;
import com.dci.pojo.Msg;
import com.dci.pojo.Session;
import com.dci.pojo.User;
import com.dci.util.RedisUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;

//此类中的每一个方法都需要输出执行前执行后以及执行时间，要记录日志方便排错
@Log4j2
@Service
public class UserService implements UserInterface {

    @Autowired
    UserMapper userMapper;

    public User query(User user) {
        User query = null;
        try {
            query = userMapper.query(user);
            log.info("------UserService.query()---执行成功------");
        } catch (Exception e) {
            log.error("------UserService.add()---执行失败------");
            e.printStackTrace();
        } finally {
            return query;
        }

    }

    @Transactional
    public int add(User user) {
        int ret=0;
        try {
            userMapper.add(user);
            log.info("------UserService.add()---执行成功------");
            ret=0;
        } catch (Exception e) {
            ret=1;
            e.printStackTrace();
            log.error("------UserService.add()---执行失败------");
        } finally {
            return ret;
        }
    }


    public void del(User user) {

    }

    @Autowired
    RedisUtil redisUtil;
    @Autowired
    UserInterface userInterface;
    @Autowired
    HttpServletRequest request;
    public Msg chuli(Boolean reqsession, String token,User user){
        Msg msg =null;
        if(reqsession){
            redisUtil.setIfTimeout("token:"+user.getUsername(),token,3600);
            msg = new Msg("登录成功", 200, null,token);
            return msg;
        }else{
            Boolean requser = redisUtil.exists("user:" + user.getUsername());//判断Key是否存在也就是用户名
            if(!requser){
                //缓存里没有的话去查数据库
                User retUser =userInterface.query(user);//返回出来查询到用户对象
                if(retUser==null){
                    //查得到说明有该账户
                    //把该登录人的会话信息，账号，密码存入缓存中。。。下次先去查询缓存。没有后再去查数据库
                    msg = new Msg("请先注册用户", 500, "用户不存在","");
                    return msg;
                }else if(retUser.getUsername().equals(user.getUsername())){
                    if(user.getPassword().equals(retUser.getPassword())){
                        redisUtil.sadd("user:" + user.getUsername(), user.getPassword());
                        redisUtil.sadd("sessionId", request.getSession().getId());
                        redisUtil.sadd("requestIP", request.getRemoteHost());
                        msg=new Msg("登录成功", 200, "",token);
                        redisUtil.setIfTimeout("token:"+user.getUsername(),token,600);
                        return msg;
                    }else{
                        msg=new Msg("登录失败", 500, "密码错误","");
                        return  msg;
                    }

                }

            }else{
                Boolean redisPassword = redisUtil.sIsMember("user:" + user.getUsername(), user.getPassword());
                if(redisPassword){
                    redisUtil.setIfTimeout("token:"+user.getUsername(),token,600);
                    msg = new Msg("登录成功", 200, null,token) ;
                }else{
                    msg = new Msg("登录失败", 500, "缓存中密码错误","") ;
                }

                return msg;

            }
        }
        return msg;
    }
    public  Msg  verifyInt(int  i){
        Msg msg =null;
        if(i==1){
            msg=new Msg("注册失败", 500, "请联系管理员","");
        }else{
            msg= new Msg("注册成功", 200, null,"");
        }
        return  msg;
    }

}
