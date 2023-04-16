package com.charity.hoangtrinh.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
public class CacheConfig {
    public static LoadingCache<Integer, String> accessToken;
    @Bean
    public void cacheBuilder() {
        accessToken = Caffeine.newBuilder().build(k -> "Data for " + k);
    }
    @Scheduled(cron = "59 59 23 * * ?")
    public void clearAccess() {
        accessToken.cleanUp();
    }
}
