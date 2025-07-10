package com.fraud.springprac.api.service.impl;

import com.fraud.springprac.api.dto.UserDto;
import com.fraud.springprac.api.exception.UserNotFoundException;
import com.fraud.springprac.api.model.UserEntity;
import com.fraud.springprac.api.repository.UserRepository;
import com.fraud.springprac.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
   private final UserRepository userRepository;

   @Autowired
   public UserServiceImpl(UserRepository userRepository) {
       this.userRepository = userRepository;
   }

    @Override
    public UserDto getUser(int id) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with" + id + " not found"));
        return mapToUserDto(user);
   }

    @Override
    public UserDto getUserByUsername(String username) {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User with" + username + " not found"));
        return mapToUserDto(user);
    }

    @Override
    public UserDto setUsername(int id ,String username) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with" + id + " not found"));
        user = userRepository.save(user);
        return mapToUserDto(user);
    }

    @Override
    public UserDto setPassword(int id ,String password) {
        UserEntity user =  userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with" + id + " not found"));
        user.setPassword(password);
        userRepository.save(user);
        return mapToUserDto(user);
    }


    private UserDto mapToUserDto(UserEntity user) {
       return getUserDto(user);
    }
    private UserDto getUserDto(UserEntity user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setPassword(user.getPassword());
        userDto.setUsername(user.getUsername());
        return userDto;
    }
}
