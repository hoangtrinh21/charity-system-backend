package com.charity.hoangtrinh.services;

import com.charity.hoangtrinh.config.CacheConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AccessService {
    public boolean isAccessToken(Integer userId, String token) {
        return CacheConfig.accessToken.get(userId).equals(token);
    }
}
