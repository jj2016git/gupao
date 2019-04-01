package com.example.pattern.singleton.lazy;

import java.io.Serializable;

public class StaticInnerClassSingleton implements Serializable {
    /**
     * 为防止用户通过反射创建实例，需要在构造函数中做特殊处理
     */
    private StaticInnerClassSingleton() {
        if (SingletonHolder.INSTANCE != null) {
            throw new RuntimeException("not allowed");
        }
    }

    public static StaticInnerClassSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        static final StaticInnerClassSingleton INSTANCE = new StaticInnerClassSingleton();
    }
}
