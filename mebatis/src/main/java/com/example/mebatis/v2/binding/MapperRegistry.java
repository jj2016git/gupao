package com.example.mebatis.v2.binding;

import com.example.mebatis.v2.session.DefaultSqlSession;

import java.util.HashMap;
import java.util.Map;

import static lombok.Lombok.checkNotNull;

public class MapperRegistry {
    // 接口和工厂类映射关系
    private final Map<Class<?>, MapperProxyFactory<?>> knownMappers = new HashMap<>();

    public <T> void addMapper(Class<T> clazz, Class<?> resultType) {
        knownMappers.putIfAbsent(clazz, new MapperProxyFactory<>(clazz, resultType));
    }

    public <T> T getMapper(Class<T> clazz, DefaultSqlSession defaultSqlSession) {
        MapperProxyFactory factory = knownMappers.get(clazz);
        checkNotNull(factory, "type " + clazz + " cannot be find");
        return (T) factory.newInstance(defaultSqlSession);
    }
}
