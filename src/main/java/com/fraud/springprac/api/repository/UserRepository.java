package com.fraud.springprac.api.repository;

import com.fraud.springprac.api.model.User;
import com.fraud.springprac.api.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
