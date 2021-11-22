package com.dci.pojo;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

/*天气*/
@Component
@Data
@Getter
@Setter
@NoArgsConstructor
public class Tq {
    private String id;
    //天气状态0晴天1多云2阴天3小雨4大雨5暴雨6小雪7大雪8暴雪9雨夹雪10台风11地震12海啸13干旱14火山爆发15冰雹
    private String tqzt;




}
