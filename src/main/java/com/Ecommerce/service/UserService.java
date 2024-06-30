package com.Ecommerce.service;

import com.Ecommerce.dto.UserDto;

import java.util.List;

public interface UserService {
    public UserDto addUser(UserDto dto);
    public void deleteUser(long userId);
    public UserDto updateUser(long userId,UserDto dto);
    public List<UserDto> getAllUser();
    public UserDto getById(long userId);
}
