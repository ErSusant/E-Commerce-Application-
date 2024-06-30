package com.Ecommerce.service;

import com.Ecommerce.dto.UserDto;
import com.Ecommerce.entity.User;
import com.Ecommerce.exception.ResourceNotFound;
import com.Ecommerce.payload.LoginDto;
import com.Ecommerce.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceIMPL implements UserService{
private JWTService jwtService;

    private UserRepository userRepository;

    public UserServiceIMPL(JWTService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    public User dtoToEntity(UserDto dto){
        User user=new User();
        user.setName(dto.getName());
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole());
        return user;
    }
    public UserDto entityDto (User user){
        UserDto dto1=new UserDto();
        dto1.setId(user.getId());
        dto1.setName(user.getName());
        dto1.setEmail(user.getEmail());
        dto1.setRole(user.getRole());
        dto1.setUsername(user.getUsername());
        return dto1;
    }

    @Override
    public UserDto addUser(UserDto dto) {
        User user = dtoToEntity(dto);
        User save = userRepository.save(user);
        UserDto userDto = entityDto(save);
        return userDto;
    }
    @Override
    public String verifyLogin(LoginDto loginDto) {
        Optional<User> byUsername = userRepository.findByUsername(loginDto.getUsername());
        if (byUsername.isPresent()) {
            User user = byUsername.get();
            if (BCrypt.checkpw(loginDto.getPassword(), user.getPassword())) {
                String token = jwtService.generateToken(loginDto);
                return token;
            }
        }
        return null;
    }

    @Override
    public void deleteUser(long userId) {
      userRepository.deleteById(userId);
    }

    @Override
    public UserDto updateUser(long userId, UserDto dto) {
        Optional<User> byId = userRepository.findById(userId);
        if(byId.isPresent()){
            User user = byId.get();
            user.setName(dto.getName());
            user.setUsername(dto.getUsername());
            user.setPassword(dto.getPassword());
            user.setEmail(dto.getEmail());
            user.setRole(dto.getRole());
            User save = userRepository.save(user);
            UserDto userDto = entityDto(save);
            return userDto;

        }
        return null;
    }

    @Override
    public List<UserDto> getAllUser() {
        List<User> all = userRepository.findAll();
        List<UserDto> collect = all.stream().map(a -> entityDto(a)).collect(Collectors.toList());
        return collect;
    }

    @Override
    public UserDto getById(long userId) {
        Optional<User> byId = userRepository.findById(userId);
        if(byId.isPresent()){
            User user = byId.get();
            UserDto userDto = entityDto(user);
            return userDto;
        }
        return null;
    }
}
