package com.dci.pojo;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.Date;

/*饮食记录*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Component
public class DietRecords {
    private String id;
    private String userid;//用户id
    private Date jlsj;//记录时间
    private int xz;//性质 0早饭1午饭2晚饭3上午茶4下午茶5夜宵
    private String swmc;//食物名称
    private Long cal;//卡路里
    private String version;//乐观锁
    private String hd;//喝的
    private String sg;//水果
    private String gzzt;//是否工作 0工作1不工作
    private String tqid;//天气表id
}
