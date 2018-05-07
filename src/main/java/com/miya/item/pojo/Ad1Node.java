package com.miya.item.pojo;

import lombok.*;

/*一个广告位的信息*/
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class Ad1Node {
    private String href;
    private String title;
    private String src;
    private Integer width;
    private Integer height;
}
