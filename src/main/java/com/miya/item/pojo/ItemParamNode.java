package com.miya.item.pojo;

import lombok.*;

/*一个广告位的信息*/
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class ItemParamNode {
    private String group;
    private String key;
    private String value;
}
