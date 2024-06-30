package com.Ecommerce.service;

import com.Ecommerce.dto.UserDto;
import com.Ecommerce.entity.User;
import com.Ecommerce.exception.ResourceNotFound;
import com.Ecommerce.payload.LoginDto;
import com.Ecommerce.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        else{
            throw new ResourceNotFound("UserId Not Found: "+userId);
        }
    }

    @Override
    public List<UserDto> getAllUser(int pageSize,int pageNo,String sortBy,String sortDir) {
        Pageable pageable=null;
         if(sortDir.equalsIgnoreCase("asc")){
               pageable = PageRequest.of(pageSize, pageNo, Sort.by(sortBy).ascending());
         }
        if(sortDir.equalsIgnoreCase("desc")){
            pageable = PageRequest.of(pageSize, pageNo, Sort.by(sortBy).descending());
        }
        Page<User> page = userRepository.findAll(pageable);
        List<User> content = page.getContent();
        List<UserDto> collect = content.stream().map(c -> entityDto(c)).collect(Collectors.toList());
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
        else{
            throw new ResourceNotFound("UserId Not Found: "+userId);
        }
    }
}
