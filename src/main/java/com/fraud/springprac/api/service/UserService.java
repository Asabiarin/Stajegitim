package com.fraud.springprac.api.service;

import com.fraud.springprac.api.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    UserDto getUser(int id);
    UserDto getUserByUsername(String username);
    UserDto setUsername(int id ,String username);
    UserDto setPassword(int id ,String password);
}
