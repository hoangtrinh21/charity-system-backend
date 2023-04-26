package com.charity.hoangtrinh.config;

import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.User;
import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class CacheConfig {
    public static LoadingCache<String, User> accessToken;
    @Bean
    public void cacheBuilder() {
        accessToken = Caffeine.newBuilder()
                .build(k  -> new User());
    }
    @Scheduled(cron = "59 59 23 * * ?")
    public void clearAccess() {
        accessToken.cleanUp();
    }
}
