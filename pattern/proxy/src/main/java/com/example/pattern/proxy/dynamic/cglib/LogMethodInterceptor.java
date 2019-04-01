package com.example.pattern.proxy.dynamic.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class LogMethodInterceptor implements MethodInterceptor {
    private Object target;


    public Object getInstance(Object target) {
        this.target = target;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(this);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        before(method.getName());
        Object result = methodProxy.invokeSuper(o, objects);
        after(method.getName());
        return result;
    }

    private void before(String methodName) {
        System.out.println("cglib proxy: keep record before " + methodName);
    }

    private void after(String methodName) {
        System.out.println("cglib proxy: keep record after " + methodName);
    }
}
