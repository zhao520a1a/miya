package com.miya.portal.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class IndexItemNode {
	@JSONField(name="d")
	private String image;    //图片
	@JSONField(name="e")
	private String e ="0";
	@JSONField(name="c")
	private String price;   //价格
	@JSONField(name="a")
	private String itemId; //id
	@JSONField(name="b")
	private String title; //标题
	@JSONField(name="f")
	private Integer f = 1; //标题


}
