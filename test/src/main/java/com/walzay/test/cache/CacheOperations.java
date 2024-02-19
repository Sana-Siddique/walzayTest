package com.walzay.test.cache;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CacheOperations {

    @CachePut(cacheNames = "getToken", key = "#username" )
    public String  putUserToken(String username, String value){
        return value;
    }

    @Cacheable(cacheNames = "getToken", key = "#username", unless = "#result == null")
    public String  getUserToken(String username){
        return null;
    }




}
