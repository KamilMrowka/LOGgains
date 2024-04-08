package com.log.gains.user;

import com.log.gains.exception.UserNotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long getIdByUsername(String username) {
        try {
            return userRepository.findByUsername(username).get().getId();
        } catch (Exception e) {
            throw new UserNotFoundException("User was not found");
        }
    }
}
