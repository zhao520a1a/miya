package com.miya.portal.pojo;

import lombok.*;

/*一个广告位的信息*/

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class ScrollNode {
    private String alt = "";
    private String href;
    private int index;
    private String src;
    private String ext = "";
}
