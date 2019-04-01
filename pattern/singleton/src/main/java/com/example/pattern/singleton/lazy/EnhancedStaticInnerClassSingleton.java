package com.example.pattern.singleton.lazy;

import java.io.Serializable;

public class EnhancedStaticInnerClassSingleton implements Serializable {
    /**
     * 为防止用户通过反射创建实例，需要在构造函数中做特殊处理
     */
    private EnhancedStaticInnerClassSingleton() {
        if (SingletonHolder.INSTANCE != null) {
            throw new RuntimeException("not allowed");
        }
    }

    public static EnhancedStaticInnerClassSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public Object readResolve() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        static final EnhancedStaticInnerClassSingleton INSTANCE = new EnhancedStaticInnerClassSingleton();
    }
}
