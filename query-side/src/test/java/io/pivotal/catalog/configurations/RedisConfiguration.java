package io.pivotal.catalog.configurations;

import io.pivotal.catalog.repositories.ProductRepository;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@Profile("mockRedisAndRepositoryConfig")
public class RedisConfiguration {

    @MockBean(name = "redisTemplate")
    public RedisTemplate<?,?> redisTemplate;

    @MockBean
    public ProductRepository repository;
}

