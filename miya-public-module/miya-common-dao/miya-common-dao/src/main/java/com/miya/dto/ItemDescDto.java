package com.miya.dto;


import com.golden.util.DateTimeUtil;
import com.golden.util.StringUtil;
import com.miya.entity.ItemDesc;
import lombok.*;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/* 商品描述信息 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ItemDescDto extends BaseTimeDto {

    private Long item_id;

    private String item_desc;



    
    public void setItemDesc_desc(String item_desc) {
        this.item_desc = item_desc == null ? null : item_desc.trim();
    }

    public ItemDesc converToItemDesc() {
        return new ItemDescDto.ItemDescDTOConvert().doForward(this);
    }

    public ItemDescDto converFor(ItemDesc ItemDesc) {
        return new ItemDescDto.ItemDescDTOConvert().doBackward(ItemDesc);
    }

    private static class ItemDescDTOConvert extends Converter<ItemDescDto, ItemDesc> {

        @Override
        protected ItemDesc doForward(ItemDescDto itemDescDto) {
            ItemDesc itemDesc = com.miya.entity.ItemDesc.builder().build();
            BeanUtils.copyProperties(itemDescDto, itemDesc);
            return itemDesc;
        }

        @Override
        protected ItemDescDto doBackward(ItemDesc itemDesc) {
            ItemDescDto itemDescDto = builder().build();
            BeanUtils.copyProperties(itemDesc, itemDescDto);
            itemDescDto.setItem_desc(StringUtil.isEmpty(itemDesc.getItem_desc(),""));
            itemDescDto.setCreate_time(DateTimeUtil.formatDate(itemDesc.getCreate_time(), "yyyy-MM-dd HH:mm:ss"));
            itemDescDto.setUpdate_time(DateTimeUtil.formatDate(itemDesc.getUpdate_time(), "yyyy-MM-dd HH:mm:ss"));
            return itemDescDto;
        }
    }



}