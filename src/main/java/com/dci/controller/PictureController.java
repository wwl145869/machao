package com.dci.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dci.pojo.Msg;
import com.dci.pojo.Session;
import com.dci.util.BaiduUtil;
import com.dci.util.RedisUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Log4j2
@RestController
public class PictureController {
    //识别图片上的文字
    @PostMapping("PictureController/freeImageOcr.do")
    public JSONObject  freeImageOcr(MultipartFile file, Session session){
        String updateFilePath = updateFilePath(file);
        BaiduUtil baiduUtil = new BaiduUtil();
        String url=updateFilePath;
//        String url="D:\\software2\\idea2017.2.2\\1111byte\\machao\\src\\main\\resources\\twsb\\2021115.png";
        String accessToken = baiduUtil.getAuthString("0UWSTmB1aTGc2onIx8juHZ3X", "QsLG9dpSUlkPVij0OWmFQlsKDpUGLx8C");//这个获取的图文识别的key
        JSONObject jsonObject = BaiduUtil.IdentifyAccurate_basic(accessToken,url);
        JSONObject json = new JSONObject();
        json.put("msg","请求成功");
        json.put("code","200");
        json.put("data",jsonObject.get("words_result"));
        log.info("------PictureController.freeImageOcr()---执行成功---");
        return json;
//        "D:\\software2\\idea2017.2.2\\1111byte\\machao\\src\\main\\resources\\twsb.png"
    }
    @PostMapping("PictureController/dishOcr.do")
    public Msg dishOcr(MultipartFile file,Session session){
        Msg msg = dishOcrChuli(file,session);
        log.info("------PictureController.dishOcr()---执行成功---");
        return  msg;
    }
    //识别图片中的食物
    @Autowired
    RedisUtil redisUtil;
    public Msg dishOcrChuli(MultipartFile file,Session session){
        String tokenache= (String)redisUtil.get("token:" + session.getUsername());
        Boolean exists = redisUtil.exists("user:" + session.getUsername());
        if(exists){
            if(tokenache.equals(session.getToken())){
                String updateFilePath = updateFilePath(file);
                //用户存在,令牌吻合，
                String url=updateFilePath;
                BaiduUtil baiduUtil = new BaiduUtil();
                String accessToken = baiduUtil.getAuthString("t2FxmUMBa0G1o0PmiRInCcxF", "FCFmZey2jQbGrH7hHkoEKhpHT2TdZQEG");
                JSONObject jsonObject = baiduUtil.IdentifyDish(accessToken, url);
                JSONArray jsonArray = (JSONArray)jsonObject.get("result");
                JSONObject obj = (JSONObject)jsonArray.get(0);
                String calorie = obj.getString("calorie");//卡路里calorie
                String dishName = obj.getString("name");//食物名称
                JSONObject msg = new JSONObject();
                msg.put("name",dishName);
                msg.put("calorie",calorie);
                return  new Msg("请求成功", 200,JSONObject.toJSONString(msg));
            } else{
                //令牌有误
                return  new Msg("令牌有误", 501, "请仔细检查您传入的令牌",session.getToken());
            }
        }else{
            //未登录就直接操作
            return  new Msg("未登录无法操作", 502, "不可以跳过登录，请注意您的操作是否规范",session.getToken());
        }
    }


    public  String updateFilePath(MultipartFile  file){
        //获取文件的完整名称包括扩展名
        String filepath="static/img/"+file.getOriginalFilename();
        try {
            File targetFile = new File(filepath);
            if (targetFile.exists()) {
                return filepath;
            }
            //第一次上传时，如果目录不存在，创建目录
            if (!targetFile.getParentFile().exists()) {
                targetFile.getParentFile().mkdirs();
            }
            //将文件输出到本地
            File files = new File(filepath);
            try {
                if (!files.exists()) {				//如果文件不存在则新建文件
                    files.createNewFile();
                }
                FileOutputStream output = new FileOutputStream(files);
                byte[] bytes = file.getBytes();
                output.write(bytes);				//将数组的信息写入文件中
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return filepath;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
