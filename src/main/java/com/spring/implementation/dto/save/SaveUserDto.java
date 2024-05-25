package com.spring.implementation.dto.save;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveUserDto {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
