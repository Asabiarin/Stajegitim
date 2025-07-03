package com.fraud.springprac.repository;

import com.fraud.springprac.model.User;
import com.fraud.springprac.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Users users = new Users();

    static Users getAllUsersRepo() {
        return users;
    }

    static User getUserByIdRepo(int id) {
        return users.getUserList().get(id);
    }

}
