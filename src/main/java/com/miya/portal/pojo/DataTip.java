package com.miya.portal.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.*;


/*首页下方商品列表展现*/

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class DataTip {

    @JSONField(name = "1")
    IndexItemNodes women_dress;  //女装
    @JSONField(name = "2")
    IndexItemNodes men_wear; // 男装
    @JSONField(name = "3")
    IndexItemNodes beauty;  //美妆
    @JSONField(name = "4")
    IndexItemNodes living; //居家
    @JSONField(name = "5")
    IndexItemNodes books;  //书籍
}
