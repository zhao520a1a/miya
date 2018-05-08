package com.miya.dto;

import com.golden.util.DateTimeUtil;
import com.miya.entity.ContentCat;
import lombok.*;
import org.springframework.beans.BeanUtils;

/*商品类别信息*/
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ContentCatDto extends BaseTimeDto   {
    private Long id;  //类目ID

    private Long parent_id;  //父类目ID=0时，代表的是一级的类目

    private String name;  //类目名称

    private Integer status;  //状态。可选值:1(正常),2(删除)

    private Integer sort_order;  //排列序号，表示同级类目的展现次序，如数值相等则按名称次序排列。取值范围:大于零的整数

    private Boolean is_parent;  //该类目是否为父类目，1为true，0为false



    public ContentCat converToContentCat() {
        return new ContentCatDTOConvert().doForward(this);
    }

    public ContentCatDto converFor(ContentCat contentCat) {
        return new  ContentCatDTOConvert().doBackward(contentCat);
    }

    private static class ContentCatDTOConvert extends Converter<ContentCatDto, ContentCat> {

        @Override
        protected ContentCat doForward(ContentCatDto contentCatDto) {
            ContentCat contentCat = ContentCat.builder().build();
            BeanUtils.copyProperties(contentCatDto, contentCat);
            return contentCat;
        }

        @Override
        protected ContentCatDto doBackward(ContentCat contentCat) {
            ContentCatDto contentCatDto = ContentCatDto.builder().build();
            BeanUtils.copyProperties(contentCat, contentCatDto);
            contentCatDto.setCreate_time(DateTimeUtil.formatDate(contentCat.getCreate_time(), "yyyy-MM-dd HH:mm:ss"));
            contentCatDto.setUpdate_time(DateTimeUtil.formatDate(contentCat.getUpdate_time(), "yyyy-MM-dd HH:mm:ss"));
            return contentCatDto;
        }
    }
}