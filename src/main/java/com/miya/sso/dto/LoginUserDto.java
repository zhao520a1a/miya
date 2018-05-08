package com.miya.sso.dto;

import lombok.*;


@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserDto {
    @NonNull
    private String username;
    @NonNull
    private String password;

}
