package com.miya.portal.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class IndexItemNodes {
    @JSONField(name = "1")
    private IndexItemNode a;
    @JSONField(name = "2")
    private IndexItemNode b;
    @JSONField(name = "3")
    private IndexItemNode c;
    @JSONField(name = "4")
    private IndexItemNode d;
    @JSONField(name = "5")
    private IndexItemNode e;
    @JSONField(name = "6")
    private IndexItemNode f;
    @JSONField(name = "7")
    private IndexItemNode g;
    @JSONField(name = "8")
    private IndexItemNode h;
    @JSONField(name = "9")
    private IndexItemNode i;
    @JSONField(name = "10")
    private IndexItemNode j;


}
