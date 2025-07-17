//package com.fraud.springprac.api.service.impl;
//
//import com.fraud.springprac.api.model.ActiveToken;
//import com.fraud.springprac.api.repository.ActiveTokenRepository;
//import com.fraud.springprac.api.service.ActiveTokenMigrationService;
//import jakarta.annotation.PostConstruct;
//import jakarta.transaction.Transactional;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class ActiveTokenMigrationServiceImpl implements ActiveTokenMigrationService {
//
//    private final ActiveTokenRepository activeTokenRepository;
//    private final RedisServiceImpl redisServiceImpl;
//
//    public ActiveTokenMigrationServiceImpl(ActiveTokenRepository activeTokenRepository,
//                                       RedisServiceImpl redisServiceImpl1) {
//        this.activeTokenRepository = activeTokenRepository;
//        this.redisServiceImpl = redisServiceImpl1;
//    }
//    @PostConstruct
//    @Transactional
//    public void migrateActiveTokensToRedis() {
//        List<ActiveToken> activeTokens = activeTokenRepository.findAll();
//        activeTokens.forEach(redisServiceImpl::save);
//    }
//}