package com.example.pattern.singleton.reflection;

import com.example.pattern.singleton.register.EnumSingleton;

import java.lang.reflect.Constructor;

/**
 * java.lang.NoSuchMethodException: com.example.pattern.singleton.register.EnumSingleton.<init>()
 * 不允许通过反射调用Enum构造方法
 */
public class EnumSingletonTest {
    public static void main(String[] args) {
        try {
            System.out.println(EnumSingleton.getInstance());
            Constructor constructor = EnumSingleton.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            Object object = constructor.newInstance();
            System.out.println(object == EnumSingleton.getInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
