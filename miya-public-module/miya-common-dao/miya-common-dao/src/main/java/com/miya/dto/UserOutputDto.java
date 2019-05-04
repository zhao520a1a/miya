package com.miya.dto;

import com.golden.util.DateTimeUtil;
import com.miya.entity.User;
import lombok.*;
import org.springframework.beans.BeanUtils;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserOutputDto extends BaseTimeDto {


    private Long id;

    private String view_id;

    private String username;

    private String password;

    private String phone;

    private String email;

    private String avatar; //头像


    public User converToUser() {
        return new UserOutputDto.UserOutputDtoConvert().doForward(this);
    }

    public UserOutputDto converFor(User User) {
        return new UserOutputDto.UserOutputDtoConvert().doBackward(User);
    }

    private static class UserOutputDtoConvert extends Converter<UserOutputDto, User> {

        @Override
        protected User doForward(UserOutputDto userOutputDto) {
            User user = com.miya.entity.User.builder().build();
            BeanUtils.copyProperties(userOutputDto, user);
            return user;
        }

        @Override
        protected UserOutputDto doBackward(User user) {
            UserOutputDto userOutputDto = builder().build();
            BeanUtils.copyProperties(user, userOutputDto);
            userOutputDto.setCreate_time(DateTimeUtil.formatDate(user.getCreate_time(), "yyyy-MM-dd HH:mm:ss"));
            userOutputDto.setUpdate_time(DateTimeUtil.formatDate(user.getUpdate_time(), "yyyy-MM-dd HH:mm:ss"));
            return userOutputDto;
        }
    }
}
