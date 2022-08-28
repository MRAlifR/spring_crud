package com.example.crud.service;

import com.example.crud.dto.models.UserDto;

import java.util.List;

public interface UserService {

    UserDto signUp(UserDto userDto);

    List<UserDto> findAll();

    UserDto findById(Long id);

    UserDto findByUsername(String username);

    UserDto updateProfile(Long id, UserDto userDto);

    void deleteUser(Long id);
}
