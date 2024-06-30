package com.Ecommerce.service;

import com.Ecommerce.dto.UserDto;
import com.Ecommerce.payload.LoginDto;

import java.util.List;

public interface UserService {
    public UserDto addUser(UserDto dto);
    public String verifyLogin(LoginDto loginDto);
    public void deleteUser(long userId);
    public UserDto updateUser(long userId,UserDto dto);
    public List<UserDto> getAllUser(int pageSize,int pageNo,String sortBy,String sortDir);
    public UserDto getById(long userId);
}
