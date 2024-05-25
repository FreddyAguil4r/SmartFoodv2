package com.spring.implementation.service;


import com.spring.implementation.domain.model.User;
import com.spring.implementation.domain.repository.UserRepository;
import com.spring.implementation.domain.service.UserService;
import com.spring.implementation.dto.save.SaveUserDto;
import com.spring.implementation.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User create(User user) {
        //verificar si ya existe un usuario con el mismo email
        User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser != null) {
            throw new IllegalArgumentException("Ya existe un usuario con el email: " + user.getEmail());
        }
        //ByCript
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        return userRepository.save(user);
    }

    @Override
    public User update(Long id, SaveUserDto user) {

        User userToUpdate = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado con ID: " + id));
        userToUpdate.setFirstName(user.getFirstName());
        userToUpdate.setLastName(user.getLastName());
        return userRepository.save(userToUpdate);
    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado con ID: " + id));
        userRepository.delete(user);

        return ResponseEntity.ok().build();
    }

    @Override
    public User findByEmail(String email) {
        User user= userRepository.findByEmail(email);
        if (user == null || user.getEmail().isEmpty()) {
            throw new IllegalArgumentException("No existe un usuario con el email: " + email);
        }
        return user;
    }
}
