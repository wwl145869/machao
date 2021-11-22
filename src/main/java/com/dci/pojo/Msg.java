package com.dci.pojo;

import com.alibaba.fastjson.JSONArray;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.stereotype.Component;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("统一返回类")
public class Msg {
    @ApiModelProperty("描述")
    private String msg;
    @ApiModelProperty("状态码")
    private int code;
    @ApiModelProperty("错误")
    private String error;
    @ApiModelProperty("令牌")
    private String Token;
    @ApiModelProperty("信息")
    private String info;
    @ApiModelProperty("数据")
    private JSONArray data;

    public Msg(String msg, int code, String error, String token) {
        this.msg = msg;
        this.code = code;
        this.error = error;
        Token = token;
    }

    public Msg(String msg, int code, String info) {
        this.msg = msg;
        this.code = code;
        this.info = info;
    }

    public Msg(String msg, int code, JSONArray data) {
        this.msg = msg;
        this.code = code;
        this.data = data;
    }
}
