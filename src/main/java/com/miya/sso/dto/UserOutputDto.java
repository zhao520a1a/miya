package com.miya.sso.dto;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserOutputDto implements Serializable {


    private Long id;

    private String view_id;

    private String username;

    private String password;

    private String phone;

    private String email;

    private String avatar; //头像

    private String create_time;

    private String update_time;

}
