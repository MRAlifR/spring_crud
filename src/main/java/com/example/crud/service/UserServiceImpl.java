package com.example.crud.service;

import com.example.crud.dto.mappers.UserMapper;
import com.example.crud.dto.models.UserDto;
import com.example.crud.entities.User;
import com.example.crud.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDto signUp(UserDto userDto) {
        User user = userMapper.toUser(userDto);
        user.setPassword(new BCryptPasswordEncoder().encode(userDto.getPassword()));

        User savedUser = userRepository.save(user);
        return userMapper.toUserDto(savedUser);
    }

    @Override
    public List<UserDto> findAll() {
        return userMapper.toUsersDto(userRepository.findAll());
    }

    @Override
    public UserDto findById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.map(userMapper::toUserDto).orElse(null);
    }

    @Override
    public UserDto findByUsername(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        return optionalUser.map(userMapper::toUserDto).orElse(null);
    }

    @Override
    public UserDto updateProfile(Long id, UserDto userDto) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.map(user -> {
            userMapper.updateUserFromDto(userDto, user);
            userRepository.save(user);
            return userMapper.toUserDto(user);
        }).orElse(null);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
