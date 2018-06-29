package net.engining.sccc.config;


import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCachePrefix;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.engining.sccc.config.props.CommonProperties;

/**
 * 
 * @author luxue
 *
 */
@Configuration
@EnableCaching
public class RedisCacheContextConfig extends CachingConfigurerSupport {

	@Autowired
	CommonProperties commonProperties;
	
	@Bean("redisCacheTemplate")
	public RedisTemplate<String, Serializable> redisCacheTemplate(RedisConnectionFactory factory) {
		RedisTemplate<String, Serializable> rt = new RedisTemplate<String, Serializable>();
		rt.setConnectionFactory(factory);
		Jackson2JsonRedisSerializer<Serializable> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Serializable>(Serializable.class);
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(om);
		
		rt.setKeySerializer(new StringRedisSerializer());
		rt.setValueSerializer(jackson2JsonRedisSerializer);
		rt.setHashKeySerializer(new StringRedisSerializer());
		rt.setHashValueSerializer(jackson2JsonRedisSerializer);
		rt.afterPropertiesSet();
		return rt;
	}
	
	/**
	 * 根据类名，方法名，参数组合生成缓存Key
	 * @return
	 */
	@Bean
	@Override
	public KeyGenerator keyGenerator() {
		return new KeyGenerator() {
			@Override
			public Object generate(Object target, Method method, Object... params) {
				StringBuilder sb = new StringBuilder();
				sb.append(target.getClass().getName());
				sb.append(method.getName());
				for (Object obj : params) {
					sb.append(obj.toString());
				}
				return sb.toString();
			}
		};
	}
	
	@SuppressWarnings("rawtypes")
	@Bean
    public CacheManager cacheManager(@Qualifier("redisCacheTemplate") RedisTemplate redisCacheTemplate) {
        RedisCacheManager manager = new RedisCacheManager(redisCacheTemplate);
        manager.setUsePrefix(true);
        RedisCachePrefix cachePrefix = new RedisPrefix(commonProperties.getAppname());
        manager.setCachePrefix(cachePrefix);
        // 整体缓存过期时间, 默认过期时间一周
        long expriation = 7 * 24 * 60 * 60;
        TimeUnit expireTimeUnit = TimeUnit.SECONDS;
        if(commonProperties.getExpireDuration() > 0){
        	if(commonProperties.getExpireTimeUnit() != null){
        		expireTimeUnit = commonProperties.getExpireTimeUnit();
        		switch (expireTimeUnit) {
    			case DAYS:
    				expriation = commonProperties.getExpireDuration() * 24 * 60 * 60;
    				break;
    			case HOURS:
    				expriation = commonProperties.getExpireDuration() * 60 * 60;
    				break;	
    			case MINUTES:
    				expriation = commonProperties.getExpireDuration() * 60;
    				break;
    			case SECONDS:
    				expriation = commonProperties.getExpireDuration();
    				break;	
    			default:
    				break;
    			}
        	}
        	
        }
        manager.setDefaultExpiration(expriation);
        
        // 设置针对具体业务缓存的过期时间。key和缓存过期时间，单位秒
//        Map<String, Long> expiresMap = new HashMap<>();
//        expiresMap.put("user", 1000L);
//        manager.setExpires(expiresMap);
        return manager;
    }
	
	public class RedisPrefix implements RedisCachePrefix {
		@SuppressWarnings("rawtypes")
		private final RedisSerializer serializer;
	    private final String delimiter;

	    public RedisPrefix() {
	        this(":");
	    }

	    public RedisPrefix(String delimiter) {
	        this.serializer = new StringRedisSerializer();
	        this.delimiter = delimiter;
	    }

	    @SuppressWarnings("unchecked")
		@Override
	    public byte[] prefix(String cacheName) {
	        return this.serializer.serialize(this.delimiter != null ? this.delimiter.concat(":").concat(cacheName).concat(":") : cacheName.concat(":"));
	    }
	}

}
