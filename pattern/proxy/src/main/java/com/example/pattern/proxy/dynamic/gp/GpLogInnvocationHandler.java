package com.example.pattern.proxy.dynamic.gp;

import java.lang.reflect.Method;

public class GpLogInnvocationHandler implements GpInvocationHandler {
    private Object target;

    public Object getInstance(Object target) {
        this.target = target;
        Class<?> clazz = target.getClass();
        return GpProxy.newProxyInstance(new GpClassLoader(), clazz.getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before(method.getName());
        Object result = method.invoke(target, args);
        after(method.getName());
        return result;
    }

    private void before(String methodName) {
        System.out.println("GP proxy: keep record before " + methodName);
    }

    private void after(String methodName) {
        System.out.println("GP proxy: keep record after " + methodName);
    }
}
