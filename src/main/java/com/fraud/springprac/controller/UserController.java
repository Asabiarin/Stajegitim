package com.fraud.springprac.controller;


import com.fraud.springprac.model.User;
import com.fraud.springprac.model.Users;
import com.fraud.springprac.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// REST Controller for managing employees
@RestController
@RequestMapping("/user/")
public class UserController {

    // GET endpoint to fetch all employees
    @Autowired
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("user/{id}")
    @ResponseBody
    public ResponseEntity<User> userDetail(@RequestBody User requestUser){

        int id = requestUser.getId();


        User user = userService.getUserById(id);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }



    @GetMapping("getAllUsers")
    public ResponseEntity<Users> getUsersController() {
        Users users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
}
