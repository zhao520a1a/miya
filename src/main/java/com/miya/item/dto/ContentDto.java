package com.miya.item.dto;

import lombok.*;

import java.io.Serializable;


@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ContentDto implements Serializable {
    private Long id;
    private Long category_id;
    private String title;
    private String sub_title;
    private String title_desc;
    private String url;
    private String pic1_path;
    private String pic2_path;
    private String content;
    private String create_time;
    private String update_time;

}
