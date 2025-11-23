package com.app.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();

        // Enables async caching
        cacheManager.setAsyncCacheMode(true);

        // Optional cache tuning
        cacheManager.setCacheSpecification("maximumSize=500,expireAfterWrite=10m");

        return cacheManager;
    }
}
