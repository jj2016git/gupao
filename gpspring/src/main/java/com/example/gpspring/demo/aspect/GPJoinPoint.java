package com.example.gpspring.demo.aspect;

import java.lang.reflect.Method;

public interface GPJoinPoint {
    Object getThis();

    Object[] getArguments();

    Method getMethod();

    void setUserAttribute(String key, Object value);

    Object getUserAttribute(String key);
}
