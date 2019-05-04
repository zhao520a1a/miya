package com.miya.entity;

import com.springboot.ping.mybatis.annotation.Pk;
import com.springboot.ping.mybatis.annotation.Table;
import com.springboot.ping.mybatis.extend.entity.BaseTimeModel;
import lombok.*;

/*用户信息*/
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Table("admin")
public class Admin extends BaseTimeModel {
    @Pk
    private Long id;

    private String username;

    private String password;

    private String phone;

    private String email;

    private String avatar; //头像


}