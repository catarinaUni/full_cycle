package com.ecommerce.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class UserResponseDTO {
    private Integer id;
    private String name;
    private String email;
    private String address;
    private Date createAt;
}
