package com.example.mebatis.v2.binding;

import com.example.mebatis.v2.session.DefaultSqlSession;

import java.lang.reflect.Proxy;

public class MapperProxyFactory<T> {
    private final Class<T> mapperInterface;
    private final Class<?> resultType;

    public MapperProxyFactory(Class<T> mapperInterface, Class<?> resultType) {
        this.mapperInterface = mapperInterface;
        this.resultType = resultType;
    }

    public <T> T newInstance(DefaultSqlSession defaultSqlSession) {
        return (T) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{mapperInterface}, new MapperProxy(defaultSqlSession, resultType));
    }
}
