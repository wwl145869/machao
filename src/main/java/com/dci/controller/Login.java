package com.dci.controller;



import com.dci.pojo.Msg;
import com.dci.pojo.Session;
import com.dci.pojo.User;
import com.dci.service.SessionInterface;
import com.dci.service.UserInterface;

import com.dci.util.RedisUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/*登录*/

@ResponseBody
@RestController
public class Login {
    @Autowired
    RedisUtil redisUtil;

    @Autowired
    Session session;
    @Autowired
    User user;
    @Autowired
    UserInterface userInterface;
    @Autowired
    SessionInterface sessionInterface;
    @Autowired
    HttpServletRequest request;
    //1、登录接口
    @GetMapping("Login/userLogin.do")
    public  Msg userLogin(User user){
        String token = UUID.randomUUID().toString();
        Boolean reqsession = redisUtil.sIsMember("sessionId", request.getSession().getId());//查看缓存中是否存在会话
        return userInterface.chuli(reqsession,token,user);
     }
//2、注册接口
@PostMapping("Login/userSignin.do")
public Msg  userSignin(User user){
    String uuid = UUID.randomUUID().toString();
    user.setId(uuid);
    int i = userInterface.add(user);
    return   userInterface.verifyInt(i);
}

}
