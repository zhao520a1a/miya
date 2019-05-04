package com.miya.dto;

import com.miya.entity.Item;
import com.miya.utils.DateTimeUtil;
import com.springboot.ping.mybatis.extend.entity.BaseModel;
import com.springboot.ping.mybatis.extend.entity.BaseTimeModel;
import lombok.*;
import org.springframework.beans.BeanUtils;


/* 商品信息 */
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto extends BaseModel {
    private Long id;  //商品id，同时也是商品编号

    private String title;  //商品标题

    private String sell_point;  //商品卖点

    private Long price;  //商品价格，单位为：分

    private Integer num;  //库存数量

    private String barcode;  //商品条形码

    private String image;  //商品图片

    private Byte status;  //商品状态，1-正常，2-下架，3-删除

    private String cid;  //所属类目名称

    private String create_time;

    private String update_time;


    public Item converToItem() {
        return new ItemDTOConvert().doForward(this);
    }

    public ItemDto converFor(Item item) {
        return new ItemDTOConvert().doBackward(item);
    }

    private static class ItemDTOConvert extends Converter<ItemDto, Item> {

        @Override
        protected Item doForward(ItemDto itemDto) {
            Item item = Item.builder().build();
            BeanUtils.copyProperties(itemDto, item);  // a shallow copy method,required:  the attribute of name and type is equal
            return item;
        }

        @Override
        protected ItemDto doBackward(Item item) {
            ItemDto itemDto = ItemDto.builder().build();
            BeanUtils.copyProperties(item, itemDto);
            itemDto.setCreate_time(DateTimeUtil.formatDate(item.getCreate_time(), "yyyy-MM-dd HH:mm:ss"));
            itemDto.setUpdate_time(DateTimeUtil.formatDate(item.getUpdate_time(), "yyyy-MM-dd HH:mm:ss"));
            return itemDto;
        }
    }

}