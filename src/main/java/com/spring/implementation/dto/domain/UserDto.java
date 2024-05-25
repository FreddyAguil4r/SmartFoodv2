package com.spring.implementation.dto.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
