package com.miya.sso.dto;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserInputDto implements Serializable {

    private String username;

    private String password;

    private String phone;

    private String email;

    private String create_time;

    private String update_time;


}
