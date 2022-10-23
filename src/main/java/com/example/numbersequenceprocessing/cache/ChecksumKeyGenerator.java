package com.example.numbersequenceprocessing.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Slf4j
@Component
public class ChecksumKeyGenerator implements KeyGenerator {
    @Override
    public Object generate(Object target, Method method, Object... params) {
        if (params[0] != null && params[0] instanceof String) {
            return method.getName() + "_" + params[0];
        }
        return null;
    }
}
