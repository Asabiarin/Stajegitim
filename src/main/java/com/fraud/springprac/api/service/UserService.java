package com.fraud.springprac.api.service;

import com.fraud.springprac.api.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    UserDto createUser(UserDto userDto);
    List<UserDto> getAllUsers();
    UserDto getUserById(int id);
    UserDto updateUser(UserDto userDto , int id);
    void deleteUserById(int id);
}
