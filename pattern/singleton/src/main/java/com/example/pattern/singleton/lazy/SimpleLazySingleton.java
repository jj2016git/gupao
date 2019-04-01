package com.example.pattern.singleton.lazy;

/**
 * 多线程会破坏单例
 */
public class SimpleLazySingleton {
    private static SimpleLazySingleton instance;

    private SimpleLazySingleton() {
    }

    public static SimpleLazySingleton getInstance() {
        if (instance == null) {
            instance = new SimpleLazySingleton();
        }
        return instance;
    }
}
