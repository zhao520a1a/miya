package com.miya.service;

import com.golden.pojo.RespObject;
import com.golden.pojo.ResponseModal;
import com.miya.dto.LoginUserDto;
import com.miya.dto.UserInputDto;
import com.miya.entity.User;


public interface UserService  {
    ResponseModal checkData(String data, int type);

    ResponseModal register(UserInputDto user);

    RespObject<String> login(LoginUserDto loginUserDto);

    User getUserByToken(String token);

    void logout(String token);

}
