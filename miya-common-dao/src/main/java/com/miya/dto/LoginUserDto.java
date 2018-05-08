package com.miya.dto;

import com.miya.entity.User;
import com.springboot.ping.mybatis.extend.entity.BaseModel;
import lombok.*;
import org.springframework.beans.BeanUtils;


@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserDto extends BaseModel{
    @NonNull
    private String username;
    @NonNull
    private String password;


    public User converToUser() {
        return new LoginUserDto.LoginUserDtoConvert().doForward(this);
    }

    public LoginUserDto converFor(User User) {
        return new LoginUserDto.LoginUserDtoConvert().doBackward(User);
    }

    private static class LoginUserDtoConvert extends Converter<LoginUserDto, User> {

        @Override
        protected User doForward(LoginUserDto LoginUserDto) {
            User User = com.miya.entity.User.builder().build();
            BeanUtils.copyProperties(LoginUserDto, User);  // a shallow copy method,required:  the attribute of name and type is equal
            return User;
        }

        @Override
        protected LoginUserDto doBackward(User User) {
            LoginUserDto LoginUserDto = builder().build();
            BeanUtils.copyProperties(User, LoginUserDto);
            return LoginUserDto;
        }
    }
}
