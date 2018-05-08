package com.miya.dto;

import com.golden.util.DateTimeUtil;
import com.miya.entity.ItemCat;
import lombok.*;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.InvocationTargetException;

/*商品类别信息*/
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ItemCatDto extends BaseTimeDto   {
    private Long id;  //类目ID

    private Long parent_id;  //父类目ID=0时，代表的是一级的类目

    private String name;  //类目名称

    private Integer status;  //状态。可选值:1(正常),2(删除)

    private Integer sort_order;  //排列序号，表示同级类目的展现次序，如数值相等则按名称次序排列。取值范围:大于零的整数

    private Boolean is_parent;  //该类目是否为父类目，1为true，0为false



    public ItemCat converToItemCat()  {
        return new ItemCatDTOConvert().doForward(this);
    }

    public ItemCatDto converFor(ItemCat itemCat) {
        return new  ItemCatDTOConvert().doBackward(itemCat);
    }

    private static class ItemCatDTOConvert extends Converter<ItemCatDto, ItemCat> {

        @Override
        protected ItemCat doForward(ItemCatDto itemCatDto)  {
            ItemCat itemCat = ItemCat.builder().build();
            BeanUtils.copyProperties(itemCatDto, itemCat);
            return itemCat;
        }

        @Override
        protected ItemCatDto doBackward(ItemCat itemCat) {
            ItemCatDto itemCatDto = ItemCatDto.builder().build();
            BeanUtils.copyProperties(itemCat, itemCatDto);
            itemCatDto.setCreate_time(DateTimeUtil.formatDate(itemCat.getCreate_time(), "yyyy-MM-dd HH:mm:ss"));
            itemCatDto.setUpdate_time(DateTimeUtil.formatDate(itemCat.getUpdate_time(), "yyyy-MM-dd HH:mm:ss"));
            return itemCatDto;
        }
    }
}