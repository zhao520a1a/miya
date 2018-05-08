package com.miya.dto;

import com.golden.util.DateTimeUtil;
import com.miya.entity.Item;
import com.springboot.ping.mybatis.extend.entity.BaseModel;
import lombok.*;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.text.NumberFormat;


/* 商品信息 */
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ItemOutputDto extends BaseModel {
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





    public Item converToItem() {
        return new ItemDTOConvert().doForward(this);
    }

    public ItemOutputDto converFor(Item item) {
        ItemOutputDto itemOutputDto = new ItemDTOConvert().doBackward(item);
        return itemOutputDto;
    }

    private static class ItemDTOConvert extends Converter<ItemOutputDto, Item> {



        @Override
        protected Item doForward(ItemOutputDto itemDto) {
            Item item = Item.builder().build();
            BeanUtils.copyProperties(itemDto, item);  // a shallow copy method,required:  the attribute of name and type is equal
            return item;
        }

        @Override
        protected ItemOutputDto doBackward(Item item) {
            ItemOutputDto itemDto = builder().build();
            BeanUtils.copyProperties(item, itemDto);
            itemDto.setId(item.getId().toString());
            itemDto.setView_id( item.getCid() + "-" +item.getId());

//            -----  String price
//            BigDecimal price = new BigDecimal(item.getPrice().floatValue()/100);
//            NumberFormat currency = NumberFormat.getCurrencyInstance();    //建立货币格式化引用
//            itemDto.setPrice( currency.format(price));


            BigDecimal satisfy_rate = new BigDecimal(item.getSatisfy_rate().floatValue()/100 );
            NumberFormat percent = NumberFormat.getPercentInstance();     //建立百分比格式化用
            percent.setMaximumFractionDigits(2);               //百分比小数点最多2位

            itemDto.setSatisfy_rate(percent.format(satisfy_rate));


            //商品状态
            Byte fromStatus = item.getStatus();
            String toStatus = "";
            if(fromStatus != null) {
                if(fromStatus == 1) {
                    toStatus = "正常";
                } else if(fromStatus == 2) {
                    toStatus = "下架";
                } else if(fromStatus == 3) {
                    toStatus = "删除";
                } else {
                    toStatus = "暂无";
                }
            } else {
                toStatus = "暂无";
            }
            itemDto.setStatus(toStatus);



            itemDto.setCreate_time(DateTimeUtil.formatDate(item.getCreate_time(), "yyyy-MM-dd HH:mm:ss"));
            itemDto.setUpdate_time(DateTimeUtil.formatDate(item.getUpdate_time(), "yyyy-MM-dd HH:mm:ss"));
            return itemDto;
        }
    }

}