package com.miya.portal.pojo;

import lombok.*;

/*一个广告位的信息*/

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class LinkNode {
    private String href;
    private String title;
    private String src;
}
