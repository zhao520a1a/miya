package com.miya.dto;

import com.miya.entity.Item;
import lombok.*;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;


@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ItemInputDto {


    private Long id;   //商品标题

    private String title;  //商品标题

    private String sell_point;  //商品卖点

    private Long price;  //商品价格

    private Integer stock;  //库存数量

    private String barcode;  //商品条形码

    private String image;  //商品图片

    private String description;

    private Long cid;  //所属类目id

    private Byte sold_cat; //0:表示新品  1：普通商品   2：热门品  3：推荐品

    private String specsStr;



    public Item converToItem() {
        return new ItemInputDto.ItemDTOConvert().doForward(this);
    }


    private static class ItemDTOConvert extends Converter<ItemInputDto, Item> {

        @Override
        protected Item doForward(ItemInputDto itemInputDto) {
            Item item = Item.builder().build();
            BeanUtils.copyProperties(itemInputDto, item);
            return item;
        }

        @Override
        protected ItemInputDto doBackward(Item item) {
            return null;
        }
    }

}
