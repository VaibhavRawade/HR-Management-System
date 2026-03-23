/*
package com.example.ReactJS_CRUD.controller;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.data.redis.core.RedisTemplate;

@Component
public class RedisTestRunner implements CommandLineRunner 
{

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisTestRunner(RedisTemplate<String, Object> redisTemplate) 
    {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void run(String... args) throws Exception 
    {
        // Save a value
        redisTemplate.opsForValue().set("myKey", "Hello Redis!");

        // Read the value
        String value = (String) redisTemplate.opsForValue().get("myKey");
        System.out.println("Value from Redis: " + value);
    }
}
*/