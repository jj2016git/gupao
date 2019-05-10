package com.example.mebatis.v2;

public interface Executor {
    <T> T query(String sql, Object[] args, Class<T> resultType);
}
