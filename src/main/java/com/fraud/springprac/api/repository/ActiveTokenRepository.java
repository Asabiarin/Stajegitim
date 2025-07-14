package com.fraud.springprac.api.repository;

import com.fraud.springprac.api.model.ActiveToken;
import com.fraud.springprac.api.model.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface ActiveTokenRepository extends JpaRepository<ActiveToken, Integer> {
    Optional<ActiveToken> findByToken(String token);
//    Stream<ActiveToken> getActiveTokenByUser(UserEntity user);
    @Transactional
    void deleteByUser(UserEntity user);
}
