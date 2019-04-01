package com.example.pattern.singleton.reflection;

import com.example.pattern.singleton.lazy.StaticInnerClassSingleton;

import java.lang.reflect.Constructor;

public class StaticInnerClassSingletonTest {
    public static void main(String[] args) {
        try {
            System.out.println(StaticInnerClassSingleton.getInstance());
            Constructor constructor = StaticInnerClassSingleton.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            Object object = constructor.newInstance();
            System.out.println(object == StaticInnerClassSingleton.getInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
