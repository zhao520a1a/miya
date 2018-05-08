package com.miya.dto;

import com.golden.util.DateTimeUtil;
import com.miya.entity.Admin;
import com.springboot.ping.mybatis.annotation.Pk;
import lombok.*;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;


@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AdminDto extends BaseTimeDto {

    private Long id;

    private String username;

    private String phone;

    private String email;

    private String avatar; //头像

    private String admin; //权限

    private String create_time;

    private String update_time;



    public Admin converToAdmin() {
        return new AdminDto.AdminDTOConvert().doForward(this);
    }

    public AdminDto converFor(Admin admin) {
        return new AdminDto.AdminDTOConvert().doBackward(admin);
    }

    private static class AdminDTOConvert extends Converter<AdminDto, Admin> {

        @Override
        protected Admin doForward(AdminDto adminDto) {
            Admin admin = com.miya.entity.Admin.builder().build();
            BeanUtils.copyProperties(adminDto, admin);  // a shallow copy method,required:  the attribute of name and type is equal
            return admin;
        }

        @Override
        protected AdminDto doBackward(Admin admin) {
            AdminDto adminDto =  builder().build();
            BeanUtils.copyProperties(admin, adminDto);
            adminDto.setCreate_time(DateTimeUtil.formatDate(admin.getCreate_time(), "yyyy-MM-dd HH:mm:ss"));
            adminDto.setUpdate_time(DateTimeUtil.formatDate(admin.getUpdate_time(), "yyyy-MM-dd HH:mm:ss"));
            return adminDto;
        }
    }
}
