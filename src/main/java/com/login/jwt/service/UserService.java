package com.login.jwt.service;

import com.login.jwt.model.User;
import com.login.jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User create(final User user){
        if(user==null || user.getUsername()==null){
            throw new RuntimeException("Invalid arguments");
        }

        final String username=user.getUsername(); // 개발 안정성을 위해 final 선언

        if(userRepository.existsByUsername(username)){
            log.warn("Username already exists {}",username);
            throw new RuntimeException("Username already exists");
        }

        return userRepository.save(user);
    }


    public User getByCredentials(String username, String password, PasswordEncoder passwordEncoder) {
        User originalUser=userRepository.findByUsername(username);

        if(originalUser!=null && passwordEncoder.matches(password,originalUser.getPassword())) return originalUser;

        return null;
    }
}
