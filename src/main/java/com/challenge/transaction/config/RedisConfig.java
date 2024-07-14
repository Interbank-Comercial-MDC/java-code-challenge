package com.challenge.transaction.config;

import com.challenge.transaction.cache.NameSpaceCacheConstant;
import com.challenge.transaction.model.Transaction;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.convert.RedisCustomConversions;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableCaching
public class RedisConfig {

  private final Logger logger = LoggerFactory.getLogger(RedisConfig.class);

  @Bean
  public JedisConnectionFactory jedisConnectionFactory() {
    RedisProperties properties = redisProperties();
    RedisStandaloneConfiguration config =
        new RedisStandaloneConfiguration(properties.getHost(), properties.getPort());

    config.setDatabase(properties.getDatabase());
    config.setPassword(properties.getPassword());

    logger.info(
        String.format(
            "Spring Redis connection properties. Host: %s,  Port:%s",
            properties.getHost(), properties.getPort()));

    return new JedisConnectionFactory(config);
  }

  @Bean
  public RedisProperties redisProperties() {
    return new RedisProperties();
  }

  @Bean
  public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
    RedisCacheConfiguration rcc =
        RedisCacheConfiguration.defaultCacheConfig()
            .serializeValuesWith(
                SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
            .prefixCacheNameWith(NameSpaceCacheConstant.GENERAL_NAME_CACHE);

    return (builder) ->
        builder.withCacheConfiguration(
            NameSpaceCacheConstant.TRANSACTION_OPERATION, rcc.entryTtl(Duration.ofMinutes(10)));
  }
}
