package com.fraud.springprac.api.service.impl;

import com.fraud.springprac.api.model.ActiveToken;
import com.fraud.springprac.api.repository.ActiveTokenRepository;
import com.fraud.springprac.api.service.ActiveTokenMigrationService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ActiveTokenMigrationServiceImpl implements ActiveTokenMigrationService {

    private final ActiveTokenRepository activeTokenRepository;
    private final RedisServiceImpl redisService;

    public ActiveTokenMigrationServiceImpl(ActiveTokenRepository activeTokenRepository,
                                           RedisServiceImpl redisService) {
        this.activeTokenRepository = activeTokenRepository;
        this.redisService = redisService;
    }

    @PostConstruct
    @Transactional
    @Override
    public void migrateActiveTokensToRedis() {
        List<ActiveToken> activeTokens = activeTokenRepository.findAll();

        activeTokens.forEach(token -> {
            // Calculate remaining time for absolute expiration
            long absoluteExpirationMs = token.getAbsoluteExpiration().getTime() - new Date().getTime();

            // Use fixed 30 minutes for sliding expiration (adjust as needed)
            long slidingExpirationMs = token.getSlidingExpiration().getTime() - new Date().getTime();

            if (absoluteExpirationMs > 0 && slidingExpirationMs > 0) {
                redisService.saveToken(token, slidingExpirationMs, absoluteExpirationMs);
            }
        });
    }
}