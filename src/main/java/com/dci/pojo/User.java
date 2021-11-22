package com.dci.pojo;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class User {
    private String id;//用户id
    private String username;//姓名
    private String password;//密码
    private String cs;//城市
    private int xb;//姓别0男1女
    private String version;//乐观锁
}
