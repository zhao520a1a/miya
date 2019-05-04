package com.miya.dto;

import com.golden.util.DateTimeUtil;
import com.miya.entity.User;
import lombok.*;
import org.springframework.beans.BeanUtils;
import org.springframework.util.DigestUtils;

import java.util.Date;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserInputDto extends BaseTimeDto {

    private String username;

    private String password;

    private String phone;

    private String email;

    private String avatar;


    public User converToUser() {
        return new UserInputDto.UserInputDtoConvert().doForward(this);
    }

    public UserInputDto converFor(User User) {
        return new UserInputDto.UserInputDtoConvert().doBackward(User);
    }

    private static class UserInputDtoConvert extends Converter<UserInputDto, User> {
        @Override
        protected User doForward(UserInputDto userInputDto) {
            User user = com.miya.entity.User.builder().build();
            BeanUtils.copyProperties(userInputDto, user);
            //补全pojo的属性
            user.setCreate_time(new Date());
            user.setUpdate_time(new Date());
            //密码要进行md5加密
            String md5Pass = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
            user.setPassword(md5Pass);
            return user;
        }

        @Override
        protected UserInputDto doBackward(User user) {
            UserInputDto userInputDto =  builder().build();
            BeanUtils.copyProperties(user, userInputDto);
            userInputDto.setCreate_time(DateTimeUtil.formatDate(user.getCreate_time(), "yyyy-MM-dd HH:mm:ss"));
            userInputDto.setUpdate_time(DateTimeUtil.formatDate(user.getUpdate_time(), "yyyy-MM-dd HH:mm:ss"));
            return userInputDto;
        }
    }

}
