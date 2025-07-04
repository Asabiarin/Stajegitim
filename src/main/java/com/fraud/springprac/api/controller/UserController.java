package com.fraud.springprac.api.controller;


import com.fraud.springprac.api.dto.UserDto;
import com.fraud.springprac.api.exception.UserNotFoundException;
import com.fraud.springprac.api.model.User;
import com.fraud.springprac.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// REST Controller for managing employees
@RestController
@RequestMapping("/api/")
public class UserController {

    // GET endpoint to fetch all employees
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("user/byId")
    public ResponseEntity<UserDto> getUserByIdWithBody(@RequestBody UserDto requestDto) throws UserNotFoundException {
       UserDto userDto = userService.getUserById(requestDto.getId());
           return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @GetMapping("user")
    public ResponseEntity<List<UserDto>> getUsers() {
    return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("user/{id}")
    public ResponseEntity<UserDto> userDetail(@PathVariable int id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping("user/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
      return new ResponseEntity<>(userService.createUser(userDto),HttpStatus.CREATED);
    }

    @PutMapping("user/{id}/update")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, @PathVariable("id")int id){
        return ResponseEntity.ok(userService.updateUser(userDto, id));
    }

    @DeleteMapping("user/{id}/delete")
    public ResponseEntity<String> deleteUser(@PathVariable("id") int id) {
        userService.deleteUserById(id);
        return new ResponseEntity<>("User with id " + id + " deleted", HttpStatus.OK);
    }

}
