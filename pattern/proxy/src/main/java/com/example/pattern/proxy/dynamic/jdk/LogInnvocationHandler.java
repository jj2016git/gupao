package com.example.pattern.proxy.dynamic.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class LogInnvocationHandler implements InvocationHandler {
    private Object target;

    public Object getInstance(Object target) {
        this.target = target;
        Class<?> clazz = target.getClass();
        return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before(method.getName());
        Object result = method.invoke(target, args);
        after(method.getName());
        return result;
    }

    private void before(String methodName) {
        System.out.println("jdk proxy: keep record before " + methodName);
    }

    private void after(String methodName) {
        System.out.println("jdk proxy: keep record after " + methodName);
    }
}
