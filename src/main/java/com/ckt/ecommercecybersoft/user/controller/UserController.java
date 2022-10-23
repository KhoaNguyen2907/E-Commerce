package com.ckt.ecommercecybersoft.user.controller;

import com.ckt.ecommercecybersoft.common.exception.NotFoundException;
import com.ckt.ecommercecybersoft.common.utils.ProjectMapper;
import com.ckt.ecommercecybersoft.role.model.Role;
import com.ckt.ecommercecybersoft.user.dto.UserDto;
import com.ckt.ecommercecybersoft.user.model.User;
import com.ckt.ecommercecybersoft.user.model.request.UserRequestModel;
import com.ckt.ecommercecybersoft.user.model.response.UserResponseModel;
import com.ckt.ecommercecybersoft.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private ProjectMapper mapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(@RequestParam(defaultValue = "5") int pageSize, @RequestParam(defaultValue = "1") int pageNumber) {
        List<UserDto> users =  userService.findAllDto(Pageable.ofSize(pageSize).withPage(pageNumber-1), UserDto.class);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable UUID id) {
        User user = userService.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        return new ResponseEntity<>(mapper.map(user,UserDto.class), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserResponseModel> addUser(@Valid @RequestBody UserRequestModel user) {
        UserDto userDto = mapper.map(user, UserDto.class);
        System.out.println(passwordEncoder.encode(userDto.getPassword()));
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userDto = userService.saveOrUpdate(userDto, User.class);
        UserResponseModel userResponseModel = mapper.map(userDto, UserResponseModel.class);
        return new ResponseEntity<>(userResponseModel, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<UserResponseModel> updateUser(@Valid @RequestBody UserRequestModel user) {
        UserDto userDto = mapper.map(user, UserDto.class);
        userDto = userService.saveOrUpdate(userDto, User.class);
        UserResponseModel userResponseModel = mapper.map(userDto, UserResponseModel.class);
        return new ResponseEntity<>(userResponseModel, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserDto> deleteUser(@PathVariable UUID id) {
        userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
