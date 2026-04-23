package com.app.dualsharebackend.service;

import com.app.dualsharebackend.dto.UserRequestDTO;
import com.app.dualsharebackend.dto.UserResponseDTO;
import com.app.dualsharebackend.mapper.UserMapper;
import com.app.dualsharebackend.model.User;
import com.app.dualsharebackend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserResponseDTO createOrGetUser(UserRequestDTO userRequestDTO) {

        Optional<User> userOptional = userRepository.findByEmail(userRequestDTO.getEmail());

        User user;

        if (userOptional.isPresent()) {
            user = userOptional.get();
        } else {
            user = userMapper.toEntity(userRequestDTO);
            user.setCode(generateCode());

            user=userRepository.save(user);
        }

        return userMapper.toDTO(user);
    }

    public String generateCode() {
        return "USR-" + (int) (Math.random() * 100000);
    }
}