package com.spring.implementation.domain.service;

import com.spring.implementation.domain.model.User;
import com.spring.implementation.dto.save.SaveUserDto;
import org.springframework.http.ResponseEntity;

public interface UserService {

    User create(User user);
    User update(Long id, SaveUserDto user);
    ResponseEntity<?> delete(Long id);
    User findByEmail(String email);

}
