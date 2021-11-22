package com.dci.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
@Log4j2
@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory Factory) throws Exception {
        RedisTemplate<String, Object> template = new RedisTemplate();
        template.setConnectionFactory(Factory);
        //json序列化配置
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        //String的序列化
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        //key采用String的形式序列化
        template.setKeySerializer(stringRedisSerializer);
        //hash采用String的形式序列化
        template.setHashKeySerializer(stringRedisSerializer);
        //value 采用jackson形式序列化
        template.setValueSerializer(jackson2JsonRedisSerializer);
        //hash的value也采用jackson
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        //每次重启都清空Key
        template.getConnectionFactory().getConnection().flushDb();
        log.info("------------redis：你被强化了，快去送...");
        log.info("------------马超疾跑已就绪...");
        log.info("------------不管你有多快，雷电总会追上你...");

        return template;

    }
}
