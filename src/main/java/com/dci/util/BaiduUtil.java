package com.dci.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

public class BaiduUtil {


    public  String getAuthString(String ak, String sk) {
        // 获取token地址
        String authHost = "https://aip.baidubce.com/oauth/2.0/token?";
        String getAccessTokenUrl = authHost
                // 1. grant_type为固定参数
                + "grant_type=client_credentials"
                // 2. 官网获取的 API Key
                + "&client_id=" + ak
                // 3. 官网获取的 Secret Key
                + "&client_secret=" + sk;
        try {
            URL realUrl = new URL(getAccessTokenUrl);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            // 获取所有响应头字段
//            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
//            for (String key : map.keySet()) {
//                System.err.println(key + "--->" + map.get(key));
//            }
            // 定义 BufferedReader输入流来读取URL的响应
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            JSONObject jsonObject = JSONObject.parseObject(result.toString());
            return jsonObject.getString("access_token");
        } catch (Exception e) {
            System.err.printf("获取token失败！");
            e.printStackTrace(System.err);
        }
        return null;
    }
    public static JSONObject IdentifyAccurate_basic(String accessToken,String filePath){
        JSONObject data =null;
        // 通用识别url
        String otherHost = "https://aip.baidubce.com/rest/2.0/ocr/v1/accurate_basic";
        // 本地图片路径
//            String filePath = "D:\\software2\\idea2017.2.2\\1111byte\\machao\\src\\main\\resources\\twsb.png";
        try {
            String imgStr = BaseImg64.getImageStrFromPath(filePath);
            String params = URLEncoder.encode("image", "UTF-8") + "=" + imgStr;
            String result = HttpUtil.post(otherHost, accessToken, params);//发送请求
            //解析JSON串
            data= JSONObject.parseObject(result);
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
    public static JSONObject IdentifyDish(String accessToken,String filePath){
        JSONObject data =null;
        // 菜品图像识别url
        String otherHost = "https://aip.baidubce.com/rest/2.0/image-classify/v2/dish";
        try {
            String imgStr = BaseImg64.getImageStrFromPath(filePath);
            String params = URLEncoder.encode("image", "UTF-8") + "=" + imgStr;
            /**
             * 线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
             */
//            accessToken  可设置缓存
            String result = HttpUtil.post(otherHost, accessToken, params);//发送请求
            //解析JSON串
            data= JSONObject.parseObject(result);

            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public static String checkFile(String path) throws Exception {
        File file = new File(path);
        if (!file.exists()) {
            throw new NullPointerException("图片不存在");
        }
        String image = BaseImg64.getImageStrFromPath(path);

        return image;
    }

}
