package com.Ecommerce.controller;

import com.Ecommerce.dto.UserDto;
import com.Ecommerce.payload.JWTTokenDto;
import com.Ecommerce.payload.LoginDto;
import com.Ecommerce.repository.UserRepository;
import com.Ecommerce.service.UserServiceIMPL;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/User")
public class UserController {
    private UserServiceIMPL userServiceIMPL;
   private UserRepository userRepository;
    public UserController(UserServiceIMPL userServiceIMPL, UserRepository userRepository) {
        this.userServiceIMPL = userServiceIMPL;
        this.userRepository = userRepository;
    }
    @PostMapping("/addUser")
    public ResponseEntity<?>addUser(@Valid @RequestBody UserDto dto, BindingResult result){
        if(result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(userRepository.existsByEmail(dto.getEmail())){
            return new ResponseEntity<>("Exists Email",HttpStatus.BAD_REQUEST);
        }
        if(userRepository.existsByUsername(dto.getUsername())){
            return new ResponseEntity<>("Exists Username",HttpStatus.BAD_REQUEST);
        }
        dto.setPassword(BCrypt.hashpw(dto.getPassword(),BCrypt.gensalt(10)));
        UserDto userDto = userServiceIMPL.addUser(dto);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<?>verifyLogin(@RequestBody LoginDto loginDto){
        String token = userServiceIMPL.verifyLogin(loginDto);
        if(token!=null){
            JWTTokenDto jwtTokenDto=new JWTTokenDto();
            jwtTokenDto.setType("JWT Token");
            jwtTokenDto.setToken(token);
            return new ResponseEntity<>(jwtTokenDto,HttpStatus.OK);
        }
         else{
            return new ResponseEntity<>("Invalid Username/Password",HttpStatus.OK);
        }
    }
    @DeleteMapping
    public ResponseEntity<String>deleteUser(@RequestParam long userId){
        userServiceIMPL.deleteUser(userId);
        return new ResponseEntity<>("Record Deleted",HttpStatus.OK);
    }
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto>updateUser(
            @PathVariable long userId,@RequestBody UserDto dto){
        dto.setPassword(BCrypt.hashpw(dto.getPassword(),BCrypt.gensalt(10)));
        UserDto userDto = userServiceIMPL.updateUser(userId, dto);
        return new ResponseEntity<>(userDto,HttpStatus.OK);
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<UserDto>>getAllUser(){
        List<UserDto> allUser = userServiceIMPL.getAllUser();
        return new ResponseEntity<>(allUser,HttpStatus.OK);
    }
    @GetMapping("/getUserId")
    public ResponseEntity<UserDto>getById(@RequestParam long userId){
        UserDto byId = userServiceIMPL.getById(userId);
        return new ResponseEntity<>(byId,HttpStatus.CREATED);
    }
}
