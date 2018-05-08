package com.miya.dto;

import com.golden.util.DateTimeUtil;
import com.miya.entity.Content;
import lombok.*;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;


@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ContentDto implements Serializable {
    private Long id;
    private Long category_id;
    private String category_name;
    private String title;
    private String sub_title;
    private String title_desc;
    private String url;
    private String pic1_path;
    private String pic2_path;
    private String content;
    private String create_time;
    private String update_time;


    public Content converToContent() {
        return new ContentDto.ContentDTOConvert().doForward(this);
    }

    public ContentDto converFor(Content Content) {
        return new ContentDto.ContentDTOConvert().doBackward(Content);
    }

    private static class ContentDTOConvert extends Converter<ContentDto, Content> {

        @Override
        protected Content doForward(ContentDto ContentDto)   {
            Content Content = com.miya.entity.Content.builder().build();
            BeanUtils.copyProperties(ContentDto, Content);  // a shallow copy method,required:  the attribute of name and type is equal
            return Content;
        }

        @Override
        protected ContentDto doBackward(Content content)  {
            ContentDto contentDto = builder().build();
            BeanUtils.copyProperties(content, contentDto);
            contentDto.setCreate_time(DateTimeUtil.formatDate(content.getCreate_time(), "yyyy-MM-dd HH:mm:ss"));
            contentDto.setUpdate_time(DateTimeUtil.formatDate(content.getUpdate_time(), "yyyy-MM-dd HH:mm:ss"));
            return contentDto;
        }
    }
}
