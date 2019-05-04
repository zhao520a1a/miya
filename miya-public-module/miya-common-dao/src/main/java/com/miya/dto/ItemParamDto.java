package com.miya.dto;


import com.golden.util.DateTimeUtil;
import com.golden.util.StringUtil;
import com.miya.entity.ItemParam;
import lombok.*;
import org.springframework.beans.BeanUtils;

/* 商品描述信息 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ItemParamDto extends BaseTimeDto {

    private Long id;

    private Long item_id;  //商品ID

    private String param_data;  //参数数据，格式为json格式


    public ItemParam converToItemParam() {
        return new ItemParamDto.ItemParamDTOConvert().doForward(this);
    }

    public ItemParamDto converFor(ItemParam ItemParam) {
        return new ItemParamDto.ItemParamDTOConvert().doBackward(ItemParam);
    }

    private static class ItemParamDTOConvert extends Converter<ItemParamDto, ItemParam> {

        @Override
        protected ItemParam doForward(ItemParamDto itemParamDto) {
            ItemParam itemParam = ItemParam.builder().build();
            BeanUtils.copyProperties(itemParamDto, itemParam);
            return itemParam;
        }

        @Override
        protected ItemParamDto doBackward(ItemParam itemParam) {
            ItemParamDto itemParamDto = builder().build();
            BeanUtils.copyProperties(itemParam, itemParamDto);
            itemParamDto.setCreate_time(DateTimeUtil.formatDate(itemParam.getCreate_time(), "yyyy-MM-dd HH:mm:ss"));
            itemParamDto.setUpdate_time(DateTimeUtil.formatDate(itemParam.getUpdate_time(), "yyyy-MM-dd HH:mm:ss"));
            return itemParamDto;
        }
    }



}