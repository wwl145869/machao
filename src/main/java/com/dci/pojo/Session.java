package com.dci.pojo;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

/*会话*/
@Component
@Setter
@Getter
@NoArgsConstructor
@Data
public class Session {
    private String id;
    private String username;//用户名
    private String userid;//用户id
    private String session;//会话
    private String cookies;//
    private String Token;//令牌
    private String version;//乐观锁
    private String ip;//IP


}
