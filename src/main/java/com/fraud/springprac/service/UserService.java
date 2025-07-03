package com.fraud.springprac.service;

import com.fraud.springprac.model.User;
import com.fraud.springprac.model.Users;
import com.fraud.springprac.repository.UserRepository;
import jakarta.transaction.TransactionScoped;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static UserRepository userRepository;

    private static Users users = new Users();


    public static Users getAllUsers() {

        return UserRepository.getAllUsersRepo();
    }

    public User getUserById(int id) {
        return  UserRepository.getUserByIdRepo(id - 1);
    }
    public void addUser(User user) {
        users.getUserList().add(user);
    }
}
