package com.fraud.springprac.api.service;

import com.fraud.springprac.api.repository.ActiveTokenRepository;
import com.fraud.springprac.api.service.impl.RedisServiceImpl;

public interface ActiveTokenMigrationService {
    void migrateActiveTokensToRedis();
}
