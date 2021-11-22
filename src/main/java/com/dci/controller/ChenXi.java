package com.dci.controller;

import com.dci.pojo.Msg;
import com.dci.util.RedisUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RestController
public class ChenXi {
    @Autowired
    RedisUtil redisUtil;


@GetMapping("ChenXi/dltL.do")
public Msg dltL(){
    Boolean dlt = redisUtil.exists("dltL");
    if(dlt){
        List dltL = redisUtil.getDltL();
        log.info("------ChenXi.dltL()------执行成功--");
        return new Msg("逢赌必赢",200,dltL.toString());
    }else{
        redisUtil.setDltL();
        List dltL = redisUtil.getDltL();
        return new Msg("就赌必输",200,dltL.toString());
    }
}
    @GetMapping("ChenXi/dltR.do")
    public Msg dltR(){
        Boolean dlt = redisUtil.exists("dltR");
        if(dlt){
            List dltR = redisUtil.getDltR();
            log.info("------ChenXi.dltR()------执行成功--");
            return new Msg("幸运女神在微笑",200,dltR.toString());
        }else{
            redisUtil.setDltR();
            List dltR = redisUtil.getDltR();
            return new Msg("好运不会眷顾傻瓜",200,dltR.toString());
        }
    }

}
