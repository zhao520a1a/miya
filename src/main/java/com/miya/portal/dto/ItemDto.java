package com.miya.portal.dto;

import com.golden.util.StringUtil;
import lombok.*;


/* 商品信息 */
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {
    private String id;  //商品id

    private String view_id;  //商品编号

    private String title;  //商品标题

    private String sell_point;  //商品卖点

    private Long price;  //商品价格

    private Integer stock;  //库存数量

    private String barcode;  //商品条形码

    private String image;  //商品图片

    private String status;  //商品状态，1-正常，2-下架，3-删除

    private Long cid;  //所属类目id

    private String cname;  //所属类目名称

    private String satisfy_rate; //好评率

    private Integer express_fee;  //快递费

    private Integer evaluation_count; //评价数

    private Integer month_sales; //月销量

    private Byte sold_cat; //0:表示新品  1：普通商品   2：热门品  3：推荐品

    private String create_time;

    private String update_time;

    public String[] getImages() {
        String[] strings ;
        if (!StringUtil.isEmpty(this.image)) {
            String image2 = this.getImage();
            strings= image2.split(",");
        } else {
            strings = new String[1];
            strings[0] = " ";  //图片为空，出现加载项
        }
        return strings;
    }


}