package com.miya.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseTimeDto implements Serializable {

    private String create_time;

    private String update_time;
}
