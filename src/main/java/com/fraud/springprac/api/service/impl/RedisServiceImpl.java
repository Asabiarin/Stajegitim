package com.fraud.springprac.api.service.impl;

import com.fraud.springprac.api.mapper.ActiveTokenRedisMapper;
import com.fraud.springprac.api.model.ActiveToken;
import com.fraud.springprac.api.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements RedisService {

    private static final String KEY_PREFIX = "ActiveToken:";

    private final RedisTemplate<String, Object> redisTemplate;
    private final HashOperations<String, String, String> hashOperations;
    private final ActiveTokenRedisMapper activeTokenRedisMapper;
    //private final UserRepository userRepository;

    @Autowired
    public RedisServiceImpl(RedisTemplate<String, Object> redisTemplate,
                            ActiveTokenRedisMapper activeTokenRedisMapper) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
        this.activeTokenRedisMapper = activeTokenRedisMapper;
        //this.userRepository = userRepository;
    }


    @Override
    public void saveToken(ActiveToken activeToken, long slidingExpirationMs, long absoluteExpirationMs) {
        String redisKey = KEY_PREFIX + activeToken.getUser().getUsername();
        Map<String, String> tokenData = activeTokenRedisMapper.mapToRedis(activeToken);

        hashOperations.putAll(redisKey, tokenData);

        // Set sliding expiration as Redis TTL
        redisTemplate.expire(redisKey, slidingExpirationMs, TimeUnit.MILLISECONDS);
    }

//    @Override
//    public Optional<ActiveToken> findValidToken(String token) {
//        String redisKey = KEY_PREFIX + token;
//
//        // First check if Redis TTL (sliding expiration) is still valid
//        if (redisTemplate.getExpire(redisKey) <= 0) {
//            return Optional.empty();
//        }
//
//        Map<String, String> tokenData = hashOperations.entries(redisKey);
//        if (tokenData.isEmpty()) {
//            return Optional.empty();
//        }
//
//        // Check absolute expiration from the stored timestamp
//        long absoluteExpTimestamp = Long.parseLong((String) tokenData.get(String.valueOf(SecurityConstants.JWT_ABSOLUTE_EXPIRATION)));
//        if (System.currentTimeMillis() > absoluteExpTimestamp) {
//            redisTemplate.delete(redisKey);
//            return Optional.empty();
//        }
//
//        int userId = Integer.parseInt((String) tokenData.get("userId"));
//        UserEntity user = userRepository.findById(userId).orElseThrow(() ->
//                new RuntimeException("User not found with id: " + userId));
//
//        return Optional.of(activeTokenRedisMapper.mapFromRedis(tokenData, user));
//    }

    @Override
    public boolean extendTokenIfNotExpired(String username, long slidingExpirationMs) {
        String redisKey = KEY_PREFIX + username;

        // Check if token exists and absolute expiration hasn't passed
        String tokenData = hashOperations.entries(redisKey).get("token");
        String tokenExprData = hashOperations.entries(redisKey).get("absoluteExpiration");

        if (tokenData.isEmpty()) {
            return false;
        }

        long absoluteExpTimestamp = Long.parseLong(tokenExprData);
        //long absoluteExpTimestamp = tokenData
        if (System.currentTimeMillis() > absoluteExpTimestamp) {
            redisTemplate.delete(redisKey);
            return false;
        }

        // Only extend if absolute expiration hasn't passed
        //System.out.println("After ifNotExpired " + DateMilliStamp.timeStamp());
        return redisTemplate.expire(redisKey, slidingExpirationMs, TimeUnit.MILLISECONDS);
    }
    @Override
    public void deleteToken(String token) {
        redisTemplate.delete(KEY_PREFIX + token);
    }



}