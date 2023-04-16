package com.charity.hoangtrinh.services;

import com.charity.hoangtrinh.config.CacheConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AccessService {
    public int checkAccessToken(Integer userId, String token) {
        String authToken = CacheConfig.accessToken.get(userId);
        if (authToken == null) return HttpStatus.FORBIDDEN.value();
        if (!authToken.equals(token)) return HttpStatus.UNAUTHORIZED.value();
        return HttpStatus.OK.value();
    }
}
