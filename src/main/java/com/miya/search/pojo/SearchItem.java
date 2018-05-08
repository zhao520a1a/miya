package com.miya.search.pojo;

import com.springboot.ping.mybatis.extend.entity.BaseModel;
import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SearchItem  extends BaseModel {
	private String id;
	private String title;
	private String sell_point;
	private Long price;
	private String image;
	private String category_name;
	private String item_desc;
}
