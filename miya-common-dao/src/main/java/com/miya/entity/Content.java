package com.miya.entity;

import com.springboot.ping.mybatis.annotation.Pk;
import com.springboot.ping.mybatis.annotation.Table;
import com.springboot.ping.mybatis.extend.entity.BaseTimeModel;
import lombok.*;


/*网站内容详情*/
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Table("content")
public class Content extends BaseTimeModel{
    @Pk
    private Long id;

    private Long category_id;  //内容类目ID

    private String title;  //内容标题

    private String sub_title; //子标题

    private String title_desc;  //标题描述

    private String url;  //链接

    private String pic1_path;  //图片1

    private String pic2_path; //图片2

    private String content;  //内容


}