package com.miya.portal.pojo;

import lombok.*;

/*一个广告位的信息*/

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class AD1Node {
    private String srcB;
    private String alt;
    private Integer width;
    private Integer height;
    private String src;
    private String href;
    private Integer widthB;
    private Integer heightB;

}
