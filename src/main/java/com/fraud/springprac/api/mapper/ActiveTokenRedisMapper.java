package com.fraud.springprac.api.mapper;

import com.fraud.springprac.api.model.ActiveToken;
import com.fraud.springprac.api.model.UserEntity;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class ActiveTokenRedisMapper {

//    @CachePut(value = "REDIS_CACHE", key = "#result")
    public Map<String, String> mapToRedis(ActiveToken activeToken) {
        Map<String, String> redisData = new HashMap<>();
        redisData.put("id", String.valueOf(activeToken.getId()));
        redisData.put("token", activeToken.getToken());
        redisData.put("userId", String.valueOf(activeToken.getUser().getId()));
        redisData.put("slidingExpiration", String.valueOf(activeToken.getSlidingExpiration().getTime()));
        redisData.put("absoluteExpiration", String.valueOf(activeToken.getAbsoluteExpiration().getTime()));
        redisData.put("issuedAt", String.valueOf(activeToken.getIssuedAt().getTime()));

        return redisData;
    }

    public ActiveToken mapFromRedis(Map<String, String> redisData, UserEntity user) {
        ActiveToken activeToken = new ActiveToken();
        activeToken.setId(Long.parseLong((String) redisData.get("id")));
        activeToken.setToken((String) redisData.get("token"));
        activeToken.setUser(user);
        activeToken.setSlidingExpiration(new Date(Long.parseLong((String) redisData.get("slidingExpiration"))));
        activeToken.setAbsoluteExpiration(new Date(Long.parseLong((String) redisData.get("absoluteExpiration"))));
        activeToken.setIssuedAt(new Date(Long.parseLong((String) redisData.get("issuedAt"))));

        return activeToken;
    }
}