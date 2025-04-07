package com.ecommerce.dtos;

import com.ecommerce.entities.User;
import lombok.Data;

import java.util.Date;

@Data
public class UserDTO {
    private Integer id;
    private String name;
    private String email;
    private String address;
    private Date createAt;

    public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.address = user.getAddress();
        this.createAt = user.getCreateAt();
    }
}
